package ru.starkov.infrastructure.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.starkov.domain.spi.security.PasswordVerifier;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BcryptPasswordVerifier implements PasswordVerifier {

    @Override
    public boolean verify(char[] passwordToVerify, String encodedPassword) {
        boolean verified = BCrypt.verifyer().verify(passwordToVerify, encodedPassword).verified;
        Arrays.fill(passwordToVerify, '0');
        return verified;
    }
}
