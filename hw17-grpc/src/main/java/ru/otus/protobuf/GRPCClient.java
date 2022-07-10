package ru.otus.protobuf;

import java.util.concurrent.CountDownLatch;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();


        var latch = new CountDownLatch(1);
        var newStub = RemoteDBServiceGrpc.newStub(channel);
        RequestMessage defaultInstance = RequestMessage.newBuilder().setFirstValue(1).setLastValue(3).build();//TODO почему дефолтный конструктор не работает

        newStub.generateNumberSequence(defaultInstance, new StreamObserver<>() {

            @Override
            public void onNext(ResponseMessage responseMessage) {
                System.out.println(defaultInstance.getFirstValue() + "   -   " + defaultInstance.getLastValue());
                System.out.printf("٩(╬ʘ益ʘ╬)۶ current number is %d%n",
                        responseMessage.getCurrentValue());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\n╮(︶▽︶)╭ THAT'S ALL BY NOW! ╮(︶▽︶)╭");
                latch.countDown();
            }
        });

        latch.await();

        channel.shutdown();
    }

}
