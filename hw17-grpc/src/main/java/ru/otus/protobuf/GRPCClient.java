package ru.otus.protobuf;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int NUMBER_OF_ITERATIONS = 50;
    private static final int FIRST_VALUE = 0;
    private static final int LAST_VALUE = 30;
    private static final int ITERATIONS_NUMBER = 1;


    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();


        var valueFromServer = new AtomicLong();
        var latch = new CountDownLatch(ITERATIONS_NUMBER);
        var newStub = RemoteDBServiceGrpc.newStub(channel);
        var defaultInstance = RequestMessage.newBuilder().setFirstValue(FIRST_VALUE).setLastValue(LAST_VALUE).build();

        newStub.generateNumberSequence(defaultInstance, new StreamObserver<>() {

            @Override
            public void onNext(ResponseMessage responseMessage) {
                System.out.printf("٩(╬ʘ益ʘ╬)۶ SERVER: %d/%d%n", responseMessage.getCurrentValue(), LAST_VALUE);
                valueFromServer.set(responseMessage.getCurrentValue());
                System.out.println("LATCH " + latch.getCount());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t.toString());
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\n╮(︶▽︶)╭ THAT'S ALL BY NOW! ╮(︶▽︶)╭");
                latch.countDown();
            }
        });

        var valueFromClient = 0;

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            var atomicValue = valueFromServer.getAndSet(0);
            valueFromClient += atomicValue + 1;
            System.out.printf("٩(｡•́‿•̀｡)۶ CLIENT: %d%n", valueFromClient);
            System.out.printf("===== number of iterations: %d =====%n", i);
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }

//        latch.await(); //закомментила, чтобы дождаться, пока цикл от сервера дойдёт до нуля
        channel.shutdown();
    }

}
