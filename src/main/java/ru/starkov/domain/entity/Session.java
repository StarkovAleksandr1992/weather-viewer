package ru.starkov.domain.entity;

import lombok.*;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.SessionId;
import ru.starkov.domain.identifier.SessionIdGenerator;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.domain.spi.validation.ValidationResult;
import ru.starkov.domain.value.Login;
import ru.starkov.shared.common.Result;
import ru.starkov.shared.mark.Entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Setter(AccessLevel.PRIVATE)
public class Session implements Entity {
    private final SessionId id;
    private final CustomerId customerId;
    private final LocalDateTime expiresAt;
    private Status status;

    public static Result<Session, List<String>> create(
            SessionIdGenerator generator,
            CustomerId customerId,
            Duration sessionDurationLimit,
            ValidationManager validationManager) {

        var session = new Session(
                generator.generate(),
                customerId,
                LocalDateTime.now().plus(sessionDurationLimit),
                Status.ACTIVE
        );

        var validationResult = validationManager.validate(session);

        if (validationResult.isValid()) {
            return Result.success(session);
        }
        return Result.fail(validationResult.getErrors());
    }

    public static Session loadFromDb(
            SessionId id,
            CustomerId customerId,
            LocalDateTime expiresAt,
            Status status) {

        Objects.requireNonNull(id, "Session id cannot be null");
        Objects.requireNonNull(customerId, "Customer id cannot be null");
        Objects.requireNonNull(expiresAt, "Session time of expiration cannot be null");
        Objects.requireNonNull(status, "Session status cannot be null");
        return new Session(id, customerId, expiresAt, status);
    }

    public boolean isActive() {
        return this.status == Status.ACTIVE && this.expiresAt.isAfter(LocalDateTime.now());
    }

    public enum Status {
        ACTIVE,
        EXPIRED
    }
}
