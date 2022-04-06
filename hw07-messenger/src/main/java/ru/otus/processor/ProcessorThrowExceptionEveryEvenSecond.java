package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorThrowExceptionEveryEvenSecond implements Processor {

    //todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
    //         Секунда должна определяться во время выполнения.
    //         Тест - важная часть задания
    // Обязательно посмотрите пример к паттерну Мементо!

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionEveryEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if(dateTimeProvider.getNow().getSecond() %2 == 0) {
            throw new RuntimeException("Ooops, you did it again on the even second!!!");
        }
        return message;
    }
}
