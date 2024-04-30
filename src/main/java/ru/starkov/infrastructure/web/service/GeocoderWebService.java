package ru.starkov.infrastructure.web.service;

import ru.starkov.infrastructure.web.service.dto.SearchLocationResponseData;

import java.util.List;

public interface GeocoderWebService {
    List<SearchLocationResponseData> findLocationByName(String locationName);

}
