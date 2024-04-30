package ru.starkov.infrastructure.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.starkov.infrastructure.web.service.dto.SearchLocationResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OpenWeatherGeocoderService implements GeocoderWebService {

    @Value("${open_weather_map.api_key}")
    private String openWeatherMapApiKey;
    @Value("${open_weather_map.geocoder_url}")
    private String openWeatherMapGeocoderUrl;
    private final RestTemplate restTemplate;

    @Override
    @Cacheable(value = "locations", cacheManager = "webServicesCacheManager")
    public List<SearchLocationResponseData> findLocationByName(String locationName) {
        var uriString = UriComponentsBuilder.fromHttpUrl(this.openWeatherMapGeocoderUrl)
                .queryParam("q", locationName)
                .queryParam("appid", this.openWeatherMapApiKey)
                .queryParam("limit", "10")
                .encode()
                .build()
                .toUriString();
        var response = restTemplate.getForEntity(uriString, SearchLocationResponseData[].class);
        var searchLocationResponseData = response.getBody();
        if (searchLocationResponseData != null) {
            return new ArrayList<>(removeDuplicates(Arrays.stream(searchLocationResponseData).toList()));
        }
        throw new RuntimeException();
    }

    private TreeSet<SearchLocationResponseData> removeDuplicates(List<SearchLocationResponseData> locations) {
        var locationsWithoutDuplicates = new TreeSet<SearchLocationResponseData>(
                SearchLocationResponseData.LocationGeoComparator.getInstance());
        locationsWithoutDuplicates.addAll(locations);
        return locationsWithoutDuplicates;
    }
}
