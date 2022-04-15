package ru.otus.processor;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProcessorThrowExceptionEveryEvenSecondTest {
    @Test
    @DisplayName("Test doesn't throw when odd second")
    void evenProcessorsTest() {
        LocalDateTime oddSecondTime = LocalDateTime.now().withSecond(1);
        Message message = new Message.Builder(1L).build();

        assertDoesNotThrow(() -> new ProcessorThrowExceptionEveryEvenSecond(() -> oddSecondTime).process(message));
    }

    @Test
    @DisplayName("Test that throws when even second")
    void exceptionProcessorsTest() {
        LocalDateTime evenSecond = LocalDateTime.now().withSecond(2);
        Message message = new Message.Builder(1L).build();

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> new ProcessorThrowExceptionEveryEvenSecond(() -> evenSecond).process(message));
    }
}
