package ru.starkov.application.usecase.location.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.SaveLocationRequest;
import ru.starkov.application.dto.response.SaveLocationResponse;
import ru.starkov.application.port.LocationGateway;
import ru.starkov.application.usecase.location.SaveLocationUseCase;
import ru.starkov.domain.entity.Location;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.LocationIdGenerator;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.shared.common.Result;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveLocationUseCaseImpl implements SaveLocationUseCase {

    private final LocationGateway locationGateway;
    private final LocationIdGenerator locationIdGenerator;
    private final ValidationManager validationManager;

    @Transactional
    @Override
    public Result<SaveLocationResponse, List<String>> save(SaveLocationRequest request) {
        List<String> errors = new ArrayList<>();
        Location.create(
                        locationIdGenerator,
                        new CustomerId(request.customerId()),
                        request.locationName(),
                        request.countryCode(),
                        request.latitude(),
                        request.longitude(),
                        validationManager)
                .onSuccess(locationGateway::save)
                .onFail(errors::addAll);

        if (errors.isEmpty()) {
            return Result.success(new SaveLocationResponse());
        }
        return Result.fail(errors);
    }
}
