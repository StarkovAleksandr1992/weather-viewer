package ru.starkov.application.dto.request;

import java.util.UUID;

public record RemoveLocationRequest(UUID customerId, double latitude, double longitude) {
}
