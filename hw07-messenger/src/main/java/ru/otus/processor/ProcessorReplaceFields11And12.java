package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorReplaceFields11And12 implements Processor {

    @Override
    public Message process(Message message) {
        return message.toBuilder().field4(message.getField10().toUpperCase()).build();
    }
}
