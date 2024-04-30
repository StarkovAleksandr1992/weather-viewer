package ru.starkov.infrastructure.validation;

import org.springframework.stereotype.Component;
import ru.starkov.domain.entity.Session;
import ru.starkov.domain.spi.validation.SessionValidator;
import ru.starkov.domain.spi.validation.ValidationResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SessionValidatorImpl implements SessionValidator {
    @Override
    public ValidationResult validate(Session session) {
        List<String> errors = new ArrayList<>();
        var validationResult = new ValidationResult();
        var id = session.getId();
        var customerId = session.getCustomerId();
        var expiresAt = session.getExpiresAt();
        var status = session.getStatus();

        if (Objects.isNull(id) || Objects.isNull(id.id())) {
            errors.add("Session id cannot be null");
        }

        if (Objects.isNull(customerId) || Objects.isNull(customerId.id())) {
            errors.add("Customer id cannot be null");
        }

        if (Objects.isNull(expiresAt)) {
            errors.add("Date of session expiration cannot be null");
        }

        if (expiresAt.isBefore(LocalDateTime.now())) {
            errors.add("Session already expired and can not be created");
        }

        if (Objects.isNull(status)) {
            errors.add("Session status cannot be null");
        }
        if (errors.isEmpty()) {
            validationResult.setValid(true);
            return validationResult;
        }
        validationResult.setValid(false);
        validationResult.setErrors(errors);
        return validationResult;
    }
}
