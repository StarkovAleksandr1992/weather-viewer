package ru.starkov.infrastructure.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.starkov.domain.entity.Location;
import ru.starkov.domain.entity.Session;
import ru.starkov.domain.spi.validation.*;
import ru.starkov.domain.value.Login;
import ru.starkov.domain.value.Password;


@Component
@RequiredArgsConstructor
public class ValidationManagerImpl implements ValidationManager {

    private final LoginValidator loginValidator;
    private final LocationValidator locationValidator;
    private final PasswordValidatorImpl passwordValidator;
    private final SessionValidatorImpl sessionValidator;

    @Override
    public ValidationResult validate(Object value) {
        if (value instanceof Password password) {
            return passwordValidator.validate(password);
        } else if (value instanceof Login login) {
            return loginValidator.validate(login);
        } else if (value instanceof Location location) {
            return locationValidator.validate(location);
        } else if (value instanceof Session session) {
            return sessionValidator.validate(session);
        }

        throw new IllegalArgumentException("Unknown object type");
    }
}
