package ru.otus.protobuf.model;

//No, you cannot use a primitive type as either the request or response. You must use a message type.
public class Response {
    private long currentValue;

    public Response(long currentValue) {
        this.currentValue = currentValue;
    }

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }

}
