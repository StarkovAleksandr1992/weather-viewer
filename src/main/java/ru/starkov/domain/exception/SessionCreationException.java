package ru.starkov.domain.exception;

public class SessionCreationException extends DomainException {
    public SessionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
