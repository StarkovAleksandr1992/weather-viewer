package ru.starkov.infrastructure.web.payload;

public record AuthenticationPayload(boolean authenticated, String customerLogin) {
}
