package ru.starkov.application.usecase.location.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.RemoveLocationRequest;
import ru.starkov.application.dto.response.RemoveLocationResponse;
import ru.starkov.application.port.LocationGateway;
import ru.starkov.application.usecase.location.RemoveLocationUseCase;
import ru.starkov.shared.common.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoveLocationUseCaseImpl implements RemoveLocationUseCase {

    private final LocationGateway locationGateway;

    @Transactional
    @Override
    public Result<RemoveLocationResponse, List<String>> remove(RemoveLocationRequest request) {
        List<String> errors = new ArrayList<>();
        locationGateway.findByCustomerIdAndCoordinates(request.customerId(), request.latitude(), request.longitude())
                .ifPresentOrElse(locationGateway::delete,
                        () -> errors.add("Location with provided coordinates %e : %e not found"
                                .formatted(request.latitude(), request.longitude())));
        if (errors.isEmpty()) {
            return Result.success(new RemoveLocationResponse());
        }
        return Result.fail(errors);
    }
}
