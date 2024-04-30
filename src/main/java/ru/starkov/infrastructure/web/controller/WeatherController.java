package ru.starkov.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.starkov.infrastructure.web.mapper.WeatherPayloadMapper;
import ru.starkov.infrastructure.web.payload.WeatherPayload;
import ru.starkov.infrastructure.web.service.WeatherWebService;

@Controller
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherWebService weatherWebService;
    private final WeatherPayloadMapper weatherPayloadMapper;

    @GetMapping(value = "weather", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WeatherPayload> getWeather(@RequestParam(name = "lat") double latitude,
                                                     @RequestParam(name = "lon") double longitude,
                                                     @RequestParam(name = "name") String locationName,
                                                     @RequestParam(name = "code") String countryCode) {
        WeatherPayload weatherPayload = weatherWebService.getWeatherDataByLocationCoordinates(latitude, longitude)
                .map(weatherPayloadMapper::mapToWeatherPayload)
                .orElse(null);
        if (weatherPayload != null) {
            weatherPayload.setLocationName(locationName);
            weatherPayload.setCountryCode(countryCode);
            weatherPayload.setLatitude(latitude);
            weatherPayload.setLongitude(longitude);
            return ResponseEntity.ok(weatherPayload);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
