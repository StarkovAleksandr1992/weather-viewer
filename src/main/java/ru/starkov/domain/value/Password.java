package ru.starkov.domain.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.starkov.domain.spi.security.PasswordEncoder;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.shared.common.Result;
import ru.starkov.shared.mark.ValueObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Setter(AccessLevel.PRIVATE)
@Getter
public final class Password implements ValueObject {
    private String encodedPassword;
    private char[] password;
    private char[] repeatedPassword;

    private Password() {
    }

    public static Result<Password, List<String>> create(char[] password,
                                                        char[] repeatedPassword,
                                                        PasswordEncoder passwordEncoder,
                                                        ValidationManager validationManager) {

        Objects.requireNonNull(validationManager, "Validation manager cannot be null");
        Objects.requireNonNull(passwordEncoder, "Password encoder cannot be null");

        var cPassword = new Password();
        cPassword.setPassword(password);
        cPassword.setRepeatedPassword(repeatedPassword);


        var validationResult = validationManager.validate(cPassword);
        if (validationResult.isValid()) {

            cPassword.setEncodedPassword(passwordEncoder.encodePasswordWithGeneratedSalt(password));
            erasePlainPasswords(password, repeatedPassword);
            return Result.success(cPassword);
        } else {
            erasePlainPasswords(password, repeatedPassword);
            return Result.fail(validationResult.getErrors());
        }
    }

    private static void erasePlainPasswords(char[] password, char[] repeatedPassword) {
        Arrays.fill(password, '0');
        Arrays.fill(repeatedPassword, '0');
    }

    public static Password loadFromDb(String encodedPassword) {
        var password = new Password();
        password.setEncodedPassword(encodedPassword);
        return password;
    }
}
