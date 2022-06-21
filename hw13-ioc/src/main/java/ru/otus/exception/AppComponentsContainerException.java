package ru.otus.exception;

public class AppComponentsContainerException extends RuntimeException {
    public AppComponentsContainerException(String message) {
        super(message);
    }

    public AppComponentsContainerException(String message, Throwable cause) {
        super(message, cause);
    }

}
