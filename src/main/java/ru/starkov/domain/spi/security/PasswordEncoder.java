package ru.starkov.domain.spi.security;

public interface PasswordEncoder {
    String encodePasswordWithGeneratedSalt(char[] password);
}
