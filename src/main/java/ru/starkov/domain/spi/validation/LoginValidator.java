package ru.starkov.domain.spi.validation;

import ru.starkov.domain.value.Login;

public interface LoginValidator {

    ValidationResult validate(Login login);

}
