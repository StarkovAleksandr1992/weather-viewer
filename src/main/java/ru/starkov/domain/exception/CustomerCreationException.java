package ru.starkov.domain.exception;

public class CustomerCreationException extends DomainException {

    public CustomerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
