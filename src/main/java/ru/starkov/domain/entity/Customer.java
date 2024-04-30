package ru.starkov.domain.entity;

import lombok.*;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.CustomerIdGenerator;
import ru.starkov.domain.spi.security.PasswordEncoder;
import ru.starkov.domain.spi.security.PasswordVerifier;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.domain.value.Login;
import ru.starkov.domain.value.Password;
import ru.starkov.shared.common.Result;
import ru.starkov.shared.mark.Entity;

import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Setter(AccessLevel.PRIVATE)
public class Customer implements Entity {

    private final CustomerId id;
    private Login login;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Password password;

    public static Result<Customer, List<String>> create(
            CustomerIdGenerator idGenerator,
            String login,
            char[] password,
            char[] repeatedPassword,
            ValidationManager validationManager,
            PasswordEncoder passwordEncoder) {

        Objects.requireNonNull(idGenerator, "Customer id generator cannot be null");
        Objects.requireNonNull(validationManager, "Validation manager cannot be null");
        Objects.requireNonNull(passwordEncoder, "Password encoder cannot be null");

        List<String> errors = new ArrayList<>();
        var customer = new Customer(idGenerator.generate());

        Login.create(login, validationManager)
                .onSuccess(customer::setLogin)
                .onFail(errors::addAll);

        Password.create(password, repeatedPassword, passwordEncoder, validationManager)
                .onSuccess(customer::setPassword)
                .onFail(errors::addAll);

        if (!errors.isEmpty()) {
            return Result.fail(errors);
        }
        return Result.success(customer);
    }

    public static Customer loadFromDb(
            CustomerId id,
            Login login,
            Password password) {

        Objects.requireNonNull(id, "Customer id cannot be null");
        Objects.requireNonNull(login, "Customer login cannot be null");
        Objects.requireNonNull(password, "Customer password cannot be null");

        return new Customer(id, login, password);
    }



    public boolean checkCredentials(PasswordVerifier passwordVerifier,
                                    char[] passwordToVerify) {
        if (passwordToVerify.length < 8 || passwordToVerify.length > 20) {
            return false;
        }

        boolean verify = passwordVerifier.verify(passwordToVerify, password.getEncodedPassword());
        Arrays.fill(passwordToVerify, '0');
        return verify;
    }
}
