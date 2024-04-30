package ru.starkov.application.usecase.auth;

import ru.starkov.application.dto.request.RegistrationRequest;
import ru.starkov.application.dto.response.RegistrationResponse;
import ru.starkov.shared.common.Result;

import java.util.List;

public interface RegistrationUseCase {
    Result<RegistrationResponse, List<String>> execute(RegistrationRequest info);
}
