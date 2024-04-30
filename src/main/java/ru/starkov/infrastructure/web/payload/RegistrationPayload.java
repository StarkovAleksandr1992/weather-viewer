package ru.starkov.infrastructure.web.payload;

public record RegistrationPayload(String login, char[] password, char[] repeatedPassword) {
}
