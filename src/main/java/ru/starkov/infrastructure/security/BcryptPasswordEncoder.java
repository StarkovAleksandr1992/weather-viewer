package ru.starkov.infrastructure.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import ru.starkov.domain.spi.security.PasswordEncoder;

import java.util.Arrays;

@RequiredArgsConstructor
public class BcryptPasswordEncoder implements PasswordEncoder {

    private final int bcryptCostFactor;

    @Override
    public String encodePasswordWithGeneratedSalt(char[] password) {
        return BCrypt.withDefaults().hashToString(this.bcryptCostFactor, password);
    }
}
