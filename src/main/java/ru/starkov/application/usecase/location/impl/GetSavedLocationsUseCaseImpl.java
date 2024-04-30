package ru.starkov.application.usecase.location.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.SavedLocationRequest;
import ru.starkov.application.port.CustomerGateway;
import ru.starkov.application.port.LocationGateway;
import ru.starkov.application.usecase.location.GetSavedLocationsUseCase;
import ru.starkov.domain.entity.Location;
import ru.starkov.shared.common.Result;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetSavedLocationsUseCaseImpl implements GetSavedLocationsUseCase {

    private final LocationGateway locationGateway;

    @Transactional(readOnly = true)
    @Override
    public Result<List<Location>, List<String>> execute(SavedLocationRequest request) {

        List<Location> locations = locationGateway.findByCustomerId(request.customerId());
        return Result.success(locations);
    }
}
