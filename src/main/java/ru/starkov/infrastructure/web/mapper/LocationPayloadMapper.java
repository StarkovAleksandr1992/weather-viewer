package ru.starkov.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.starkov.application.dto.request.SaveLocationRequest;
import ru.starkov.domain.entity.Location;
import ru.starkov.infrastructure.web.payload.LocationPayload;
import ru.starkov.infrastructure.web.service.dto.SearchLocationResponseData;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LocationPayloadMapper {

    @Mapping(target = "locationName", source = "name")
    @Mapping(target = "countryCode", source = "country")
    LocationPayload mapToLocationPayload(SearchLocationResponseData searchLocationResponseData);

    @Mapping(target = "locationName", source = "name")
    LocationPayload mapToLocationPayload(Location location);

    List<LocationPayload> mapToLocationPayloadList(List<SearchLocationResponseData> searchLocationResponseDataList);

    List<LocationPayload> mapLocationsListToLocationPayloadList(List<Location> locations);

    @Mapping(target = "locationName", source = "locationPayload.locationName")
    SaveLocationRequest mapToSaveLocationRequest(LocationPayload locationPayload, UUID customerId);

}
