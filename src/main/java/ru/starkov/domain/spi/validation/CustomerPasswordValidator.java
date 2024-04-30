package ru.starkov.domain.spi.validation;


import ru.starkov.domain.value.Password;

public interface CustomerPasswordValidator {
    ValidationResult validate(Password password);
}
