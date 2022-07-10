package ru.otus.protobuf.model;

public class Request {
    private long firstValue;
    private long lastValue;

    public Request(long firstValue, long lastValue) {
        this.firstValue = firstValue;
        this.lastValue = lastValue;
    }

    public long getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(long firstValue) {
        this.firstValue = firstValue;
    }

    public long getLastValue() {
        return lastValue;
    }

    public void setLastValue(long lastValue) {
        this.lastValue = lastValue;
    }

}
