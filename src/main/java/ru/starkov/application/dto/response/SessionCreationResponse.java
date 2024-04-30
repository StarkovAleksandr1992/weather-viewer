package ru.starkov.application.dto.response;

import java.util.UUID;

public record SessionCreationResponse(UUID sessionId, int expiry) {

}
