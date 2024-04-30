package ru.starkov.application.usecase.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.SessionInfo;
import ru.starkov.application.dto.response.AuthenticationStatusResponse;
import ru.starkov.application.port.CustomerGateway;
import ru.starkov.application.port.SessionGateway;
import ru.starkov.application.usecase.auth.CheckCustomerAuthenticationStatus;
import ru.starkov.shared.common.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckCustomerAuthenticationStatusImpl implements CheckCustomerAuthenticationStatus {

    private final SessionGateway sessionGateway;
    private final CustomerGateway customerGateway;

    @Transactional(readOnly = true)
    @Override
    public Result<AuthenticationStatusResponse, List<String>> check(SessionInfo sessionInfo) {

        var sessionOptional = sessionGateway.findSessionById(sessionInfo.sessionId());

        if (sessionOptional.isEmpty()) {
            return Result.fail(List.of("Session with provided id '%s' not found".formatted(sessionInfo.sessionId())));
        }
        var session = sessionOptional.get();
        var customerOptional = customerGateway.findById(session.getCustomerId().id());
        if (customerOptional.isEmpty()) {
            return Result.fail(List.of("Customer not found"));
        }
        if (session.isActive()) {
            return Result.success(new AuthenticationStatusResponse(
                    session.getCustomerId().id(),
                    customerOptional.get().getLogin().getStringValue(),
                    true));
        } else {
            return Result.fail(List.of("Session with provided id '%s' expired".formatted(sessionInfo.sessionId())));
        }
    }
}
