package ru.starkov.application.dto.request;

import java.util.UUID;

public record SaveLocationRequest(
        UUID customerId,
        String locationName,
        String countryCode,
        double latitude,
        double longitude
) {
}
