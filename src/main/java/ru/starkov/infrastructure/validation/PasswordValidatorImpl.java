package ru.starkov.infrastructure.validation;

import org.passay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.starkov.domain.spi.validation.CustomerPasswordValidator;
import ru.starkov.domain.spi.validation.ValidationResult;
import ru.starkov.domain.value.Password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.*;

@Component
public class PasswordValidatorImpl implements CustomerPasswordValidator {

    @Value("${security.password.min_length}")
    private int minLength;

    @Value("${security.password.max_length}")
    private int maxLength;

    @Value("${security.password.num_eng_lower_case_chars}")
    private int numberOfLowerCaseEngChars;

    @Value("${security.password.num_eng_upper_case_chars}")
    private int numberOfUpperCaseEngChars;

    @Value("${security.password.num_spec_chars}")
    private int numberOfSpecialChars;

    @Override
    public ValidationResult validate(Password cPassword) {
        char[] password = cPassword.getPassword();
        char[] repeatedPassword = cPassword.getRepeatedPassword();
        var validationResult = new ValidationResult();
        List<String> errors = new ArrayList<>();

        if (arePasswordsNullOrEmpty(password, repeatedPassword)) {
            errors.add("Passwords can not be empty or blank");
            validationResult.setValid(false);
            validationResult.setErrors(errors);
            return validationResult;
        }

        if (arePasswordsMismatching(password, repeatedPassword)) {
            errors.add("Passwords do not match");
        }

        var passwordValidator = createPasswordValidator();
        var ruleResult = passwordValidator.validate(new PasswordData(String.valueOf(password)));

        if (!ruleResult.isValid()) {
            errors.addAll(passwordValidator.getMessages(ruleResult));
        }

        validationResult.setValid(errors.isEmpty());
        validationResult.setErrors(errors);
        return validationResult;
    }

    private PasswordValidator createPasswordValidator() {
        return new PasswordValidator(
                Arrays.asList(
                        new LengthRule(this.minLength, this.maxLength),
                        new CharacterRule(EnglishCharacterData.LowerCase, this.numberOfLowerCaseEngChars),
                        new CharacterRule(EnglishCharacterData.UpperCase, this.numberOfUpperCaseEngChars),
                        new CharacterRule(EnglishCharacterData.Special, this.numberOfSpecialChars),
                        new WhitespaceRule()
                )
        );
    }

    private boolean arePasswordsMismatching(char[] password, char[] repeatedPassword) {
        return !Arrays.equals(password, repeatedPassword);
    }

    private boolean arePasswordsNullOrEmpty(char[] password, char[] repeatedPassword) {
        return isNull(password) || isNull(repeatedPassword) || password.length == 0 || repeatedPassword.length == 0;
    }

}
