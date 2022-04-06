package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorUpperField10 implements Processor {

    //done: 2. Сделать процессор, который поменяет местами значения field11 и field12

    @Override
    public Message process(Message message) {
        return message.toBuilder().field11(message.getField12()).field12(message.getField11()).build();
    }
}
