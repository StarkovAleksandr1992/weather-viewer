package ru.starkov.infrastructure.web.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class WeatherResponseData {
    @JsonProperty(value = "coord")
    private Coordinates coordinates;
    @JsonProperty(value = "weather")
    private WeatherInfo[] weatherInfos;
    @JsonProperty(value = "main")
    private WeatherParameters weatherParameters;

    @Data
    public static class Coordinates {
        @JsonProperty(value = "lat")
        private double latitude;
        @JsonProperty(value = "lon")
        private double longitude;
    }

    @Data
    public static class WeatherInfo {
        @JsonProperty(value = "main")
        private String main;
        @JsonProperty(value = "description")
        private String description;
        @JsonProperty(value = "icon")
        private String icon;
    }

    @Data
    public static class WeatherParameters {
        @JsonProperty(value = "temp")
        private double temperature;
        @JsonProperty(value = "feels_like")
        private double feelsLikeTemperature;
        @JsonProperty(value = "temp_min")
        private double minTemperature;
        @JsonProperty(value = "temp_max")
        private double maxTemperature;
        @JsonProperty(value = "pressure")
        private int pressure;
        @JsonProperty(value = "humidity")
        private int humidity;
    }
}
