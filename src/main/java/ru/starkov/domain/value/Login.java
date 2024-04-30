package ru.starkov.domain.value;

import lombok.Getter;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.shared.common.Result;
import ru.starkov.shared.mark.ValueObject;

import java.util.List;
import java.util.Objects;

@Getter
public final class Login implements ValueObject {
    private final String stringValue;

    private Login(String stringValue) {
        this.stringValue = stringValue;
    }

    public static Result<Login, List<String>> create(String login, ValidationManager validationManager) {
        Objects.requireNonNull(validationManager, "Validation manager cannot be null");


        var cLogin = new Login(login);
        var validationResult = validationManager.validate(cLogin);
        if (validationResult.isValid()) {
            return Result.success(cLogin);
        } else {
            return Result.fail(validationResult.getErrors());
        }
    }

    public static Login loadFromDb(String login) {
        return new Login(login);
    }
}
