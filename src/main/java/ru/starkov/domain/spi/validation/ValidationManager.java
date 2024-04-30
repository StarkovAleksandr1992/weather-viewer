package ru.starkov.domain.spi.validation;


public interface ValidationManager {

    ValidationResult validate(Object value);
}
