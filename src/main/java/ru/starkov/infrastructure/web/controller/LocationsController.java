package ru.starkov.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.starkov.application.dto.request.RemoveLocationRequest;
import ru.starkov.application.dto.request.SavedLocationRequest;
import ru.starkov.application.usecase.location.GetSavedLocationsUseCase;
import ru.starkov.application.usecase.location.RemoveLocationUseCase;
import ru.starkov.application.usecase.location.SaveLocationUseCase;
import ru.starkov.infrastructure.web.mapper.LocationPayloadMapper;
import ru.starkov.infrastructure.web.payload.LocationPayload;
import ru.starkov.infrastructure.web.service.GeocoderWebService;

import java.util.List;
import java.util.UUID;

import static ru.starkov.infrastructure.web.util.Constants.CUSTOMER_ID_ATTRIBUTE;

@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationsController {

    private final GeocoderWebService geocoderWebService;
    private final SaveLocationUseCase saveLocationUseCase;
    private final GetSavedLocationsUseCase getSavedLocationsUseCase;
    private final RemoveLocationUseCase removeLocationUseCase;
    private final LocationPayloadMapper locationPayloadMapper;


    @GetMapping(value = "find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findLocation(@RequestParam(name = "query") String param) {
        List<LocationPayload> locations = locationPayloadMapper
                .mapToLocationPayloadList(geocoderWebService.findLocationByName(param));

        return ResponseEntity.ok(locations);
    }

    @PostMapping("save")
    public ResponseEntity<?> saveLocation(@RequestBody LocationPayload locationPayload,
                                          @RequestAttribute(CUSTOMER_ID_ATTRIBUTE) String customerId) {
        var saveLocationRequest = locationPayloadMapper.mapToSaveLocationRequest(locationPayload, UUID.fromString(customerId));
        var result = saveLocationUseCase.save(saveLocationRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.internalServerError().body(result.getErrors());
        }
    }

    @GetMapping(value = "saved", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getSavedLocation(@RequestAttribute(CUSTOMER_ID_ATTRIBUTE) String customerId) {
        var result = getSavedLocationsUseCase.execute(new SavedLocationRequest(UUID.fromString(customerId)));
        if (result.isSuccess()) {
            return ResponseEntity.ok(locationPayloadMapper.mapLocationsListToLocationPayloadList(result.getValue()));
        } else {
            return ResponseEntity.internalServerError().body(result.getErrors());
        }
    }

    @DeleteMapping(value = "remove")
    public ResponseEntity<?> removeLocation(@RequestParam(name = "lat") double latitude,
                                            @RequestParam(name = "lon") double longitude,
                                            @RequestAttribute(CUSTOMER_ID_ATTRIBUTE) String customerId) {
        var result = removeLocationUseCase.remove(new RemoveLocationRequest(UUID.fromString(customerId), latitude, longitude));
        if (result.isSuccess()) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.internalServerError().body(result.getErrors());
    }
}
