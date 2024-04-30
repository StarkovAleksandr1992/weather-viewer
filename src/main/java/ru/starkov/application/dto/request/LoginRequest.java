package ru.starkov.application.dto.request;

public record LoginRequest(String login, char[] password) {
}
