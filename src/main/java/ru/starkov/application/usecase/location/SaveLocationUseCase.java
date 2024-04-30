package ru.starkov.application.usecase.location;

import ru.starkov.application.dto.request.SaveLocationRequest;
import ru.starkov.application.dto.response.SaveLocationResponse;
import ru.starkov.shared.common.Result;

import java.util.Collection;
import java.util.List;

public interface SaveLocationUseCase {

    Result<SaveLocationResponse, List<String>> save(SaveLocationRequest saveLocationRequest);
}
