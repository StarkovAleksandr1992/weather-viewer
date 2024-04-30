package ru.starkov.application.dto.request;

import java.util.UUID;

public record SessionCreationRequest(UUID customerId) {
}
