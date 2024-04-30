package ru.starkov.application.usecase.location;

import ru.starkov.application.dto.request.RemoveLocationRequest;
import ru.starkov.application.dto.response.RemoveLocationResponse;
import ru.starkov.shared.common.Result;

import java.util.Collection;
import java.util.List;

public interface RemoveLocationUseCase {

    Result<RemoveLocationResponse, List<String>> remove(RemoveLocationRequest request);
}
