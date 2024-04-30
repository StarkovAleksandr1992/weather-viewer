package ru.starkov.application.dto.response;

import java.util.UUID;

public record AuthenticationStatusResponse(UUID customerId, String login, boolean isAuthenticated) {
}
