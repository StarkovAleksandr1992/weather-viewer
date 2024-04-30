package ru.starkov.infrastructure.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.starkov.domain.spi.validation.LoginValidator;
import ru.starkov.domain.spi.validation.ValidationResult;
import ru.starkov.domain.value.Login;
import ru.starkov.infrastructure.db.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.*;

@Component
@RequiredArgsConstructor
public class LoginValidatorImpl implements LoginValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private final CustomerRepository customerRepository;

    @Override
    public ValidationResult validate(Login login) {
        var result = new ValidationResult();
        List<String> errors = new ArrayList<>();
        var cLogin = login.getStringValue();
        if (isNull(cLogin) || cLogin.isBlank()) {
            errors.add("Login cannot be empty or blank");
            result.setValid(false);
            result.setErrors(errors);
            return result;
        }
        if (!EMAIL_PATTERN.matcher(cLogin).matches()) {
            errors.add("Login must be a valid email");
        }
        if (customerRepository.findByLogin(cLogin).isPresent()) {
            errors.add("User with provided login already exists");
        }
        result.setValid(errors.isEmpty());
        result.setErrors(errors);
        return result;
    }
}
