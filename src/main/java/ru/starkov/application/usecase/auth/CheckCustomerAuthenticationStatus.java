package ru.starkov.application.usecase.auth;

import ru.starkov.application.dto.SessionInfo;
import ru.starkov.application.dto.response.AuthenticationStatusResponse;
import ru.starkov.shared.common.Result;

import java.util.List;

public interface CheckCustomerAuthenticationStatus {

    Result<AuthenticationStatusResponse, List<String>> check(SessionInfo sessionInfo);
}
