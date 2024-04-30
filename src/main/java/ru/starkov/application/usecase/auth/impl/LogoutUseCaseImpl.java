package ru.starkov.application.usecase.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.LogoutRequest;
import ru.starkov.application.dto.response.LogoutResponse;
import ru.starkov.application.port.SessionGateway;
import ru.starkov.application.usecase.auth.LogoutUseCase;
import ru.starkov.shared.common.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final SessionGateway sessionGateway;

    @Transactional
    @Override
    public Result<LogoutResponse, List<String>> execute(LogoutRequest logoutRequest) {
        var sessionOptional = sessionGateway.findSessionById(logoutRequest.sessionId());
        if (sessionOptional.isEmpty()) {
            return Result.fail(List.of("Session not found"));
        }
        var session = sessionOptional.get();
        if (!logoutRequest.customerId().equals(session.getCustomerId().id())) {
            return Result.fail(List.of("Session does not belong to the specified customer"));
        }
        sessionGateway.delete(session.getId().id());
        return Result.success(new LogoutResponse(session.getId().id(), 0));
    }
}
