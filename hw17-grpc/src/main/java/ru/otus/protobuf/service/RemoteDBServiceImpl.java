package ru.otus.protobuf.service;

import java.util.concurrent.atomic.AtomicLong;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {

    public RemoteDBServiceImpl() {}

    @Override
    public void generateNumberSequence(RequestMessage requestMessage, StreamObserver<ResponseMessage> responseObserver) {
        AtomicLong firstValue = new AtomicLong(requestMessage.getFirstValue()); //TODO а нужны ли атомики?
        AtomicLong lastValue = new AtomicLong(requestMessage.getLastValue());
        for (long index = firstValue.longValue(); index <= lastValue.get(); index++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(ResponseMessage.newBuilder().setCurrentValue(index).build());
        }
        responseObserver.onCompleted();
    }

}
