package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorUpperField10 implements Processor {

    //done: 2. Сделать процессор, который поменяет местами значения field11 и field12

    @Override
    public Message process(Message message) {
        return message.toBuilder().field4(message.getField10().toUpperCase()).build();
    }
}
