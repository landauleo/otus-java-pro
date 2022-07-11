package ru.otus.protobuf.model;

public class Request {
    private long firstValue;
    private long lastValue;

    public Request() {
        this.firstValue = 0;
        this.lastValue = 30L;
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
