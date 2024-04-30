package ru.starkov.application.usecase.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.SessionCreationRequest;
import ru.starkov.application.dto.response.SessionCreationResponse;
import ru.starkov.application.port.SessionGateway;
import ru.starkov.application.usecase.auth.CreateSessionUseCase;
import ru.starkov.domain.entity.Session;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.SessionIdGenerator;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.shared.common.Result;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSessionUseCaseImpl implements CreateSessionUseCase {

    private final SessionGateway sessionGateway;
    private final SessionIdGenerator sessionIdGenerator;
    private final ValidationManager validationManager;

    @Value("${session.duration_of_hours}")
    private int sessionDurationOfHours;

    private static int calculateAndGetExpiry(Session session) {
        return (int) (session.getExpiresAt().toEpochSecond(ZoneOffset.UTC) -
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    }

    @Transactional
    @Override
    public Result<SessionCreationResponse, Collection<String>> create(SessionCreationRequest request) {
        List<String> errors = new ArrayList<>();

        var sessionResult = Session.create(
                        sessionIdGenerator,
                        new CustomerId(request.customerId()),
                        Duration.of(sessionDurationOfHours, ChronoUnit.HOURS),
                        validationManager)
                .onSuccess(sessionGateway::save)
                .onFail(errors::addAll);

        if (errors.isEmpty()) {
            var session = sessionResult.getValue();
            int expiry = calculateAndGetExpiry(session);
            return Result.success(new SessionCreationResponse(session.getId().id(), expiry));
        }
        return Result.fail(errors);
    }
}
