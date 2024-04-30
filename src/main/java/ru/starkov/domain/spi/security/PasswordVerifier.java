package ru.starkov.domain.spi.security;

public interface PasswordVerifier {
    boolean verify(char[] passwordToVerify, String encodedPassword);
}
