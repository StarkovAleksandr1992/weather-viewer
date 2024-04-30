package ru.starkov.infrastructure.web.service;


import ru.starkov.infrastructure.web.service.dto.SearchLocationResponseData;
import ru.starkov.infrastructure.web.service.dto.WeatherResponseData;

import java.util.Optional;

public interface WeatherWebService {

    Optional<WeatherResponseData> getWeatherDataByLocationCoordinates(double latitude, double longitude);
}
