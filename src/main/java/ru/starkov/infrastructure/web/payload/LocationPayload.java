package ru.starkov.infrastructure.web.payload;

import lombok.Data;

@Data
public class LocationPayload {

    private String locationName;
    private String countryCode;
    private double latitude;
    private double longitude;
}
