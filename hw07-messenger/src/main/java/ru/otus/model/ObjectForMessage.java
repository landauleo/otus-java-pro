package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage() {
    }

    public ObjectForMessage(ObjectForMessage object) {
        if (object == null || object.data == null) {
            throw new NullPointerException("ObjectForMessage or its data should not be null");
        }
        this.data = List.copyOf(object.data);
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}
