package ru.otus.processor;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime getNow(); //like Spring interface
}
