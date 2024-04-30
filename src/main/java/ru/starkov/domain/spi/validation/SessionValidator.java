package ru.starkov.domain.spi.validation;

import ru.starkov.domain.entity.Session;

public interface SessionValidator {
    ValidationResult validate(Session session);

}
