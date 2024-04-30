package ru.starkov.application.usecase.location;

import ru.starkov.application.dto.request.SavedLocationRequest;
import ru.starkov.domain.entity.Location;
import ru.starkov.shared.common.Result;

import java.util.List;

public interface GetSavedLocationsUseCase {

    Result<List<Location>, List<String>> execute(SavedLocationRequest request);
}
