package ru.starkov.application.usecase.auth;

import ru.starkov.application.dto.request.SessionCreationRequest;
import ru.starkov.application.dto.response.SessionCreationResponse;
import ru.starkov.shared.common.Result;

import java.util.Collection;

public interface CreateSessionUseCase {

    Result<SessionCreationResponse, Collection<String>> create(SessionCreationRequest request);
}
