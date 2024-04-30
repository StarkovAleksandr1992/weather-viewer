package ru.starkov.application.dto.request;

import java.util.UUID;

public record LogoutRequest(UUID customerId, UUID sessionId) {
}
