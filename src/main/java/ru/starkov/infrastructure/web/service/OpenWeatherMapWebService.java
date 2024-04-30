package ru.starkov.infrastructure.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.starkov.infrastructure.web.service.dto.SearchLocationResponseData;
import ru.starkov.infrastructure.web.service.dto.WeatherResponseData;
import ru.starkov.infrastructure.web.service.WeatherWebService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapWebService implements WeatherWebService {

    private final RestTemplate restTemplate;

    @Value("${open_weather_map.api_key}")
    private String openWeatherMapApiKey;
    @Value("${open_weather_map.current_weather_url}")
    private String openWeatherMapCurrenWeatherUrl;


    @Override
    @Cacheable(value = "weather", cacheManager = "webServicesCacheManager")
    public Optional<WeatherResponseData> getWeatherDataByLocationCoordinates(double latitude, double longitude) {
        var uriString = UriComponentsBuilder.fromHttpUrl(this.openWeatherMapCurrenWeatherUrl)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", this.openWeatherMapApiKey)
                .queryParam("units", "metric")
                .encode()
                .toUriString();
        var response = restTemplate.getForEntity(uriString, WeatherResponseData.class);
        return Optional.ofNullable(response.getBody());
    }

}
