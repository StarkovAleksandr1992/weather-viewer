package ru.starkov.infrastructure.web.payload;

import lombok.Data;


@Data
public class WeatherPayload {
    private String locationName;
    private String countryCode;

    private double latitude;
    private double longitude;


    private String description;
    private String detailedDescription;
    private String iconUrl;

    private int temperature;
    private int feelsLikeTemperature;
    private int minTemperature;
    private int maxTemperature;

    private int pressure;
    private int humidity;
}
