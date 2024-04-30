package ru.starkov.infrastructure.web.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginPayload(String login, char[] password) {
}
