package ru.starkov.application.dto.request;

public record RegistrationRequest(String login, char[] password, char[] repeatedPassword) {
}
