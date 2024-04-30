package ru.starkov.application.usecase.auth;

import ru.starkov.application.dto.request.LogoutRequest;
import ru.starkov.application.dto.response.LoginResponse;
import ru.starkov.application.dto.response.LogoutResponse;
import ru.starkov.shared.common.Result;

import java.util.Collection;
import java.util.List;

public interface LogoutUseCase {
    Result<LogoutResponse, List<String>> execute(LogoutRequest logoutRequest);
}
