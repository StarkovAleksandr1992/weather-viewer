package ru.starkov.application.usecase.auth;

import ru.starkov.application.dto.request.LoginRequest;
import ru.starkov.application.dto.response.LoginResponse;
import ru.starkov.shared.common.Result;

import java.util.List;

public interface LoginUseCase {
    Result<LoginResponse, List<String>> execute(LoginRequest request);
}
