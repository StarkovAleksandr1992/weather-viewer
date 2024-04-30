package ru.starkov.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.SessionId;
import ru.starkov.domain.identifier.SessionIdGenerator;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.domain.spi.validation.ValidationResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionTest {
    @Mock
    private SessionIdGenerator sessionIdGenerator;
    @Mock
    private ValidationManager validationManager;

    @Test
    void createWithValidDataTest() {
        // Arrange
        var sessionId = new SessionId(UUID.randomUUID());
        var customerId = new CustomerId(UUID.randomUUID());
        var sessionDurationLimit = Duration.ofHours(1);

        when(sessionIdGenerator.generate()).thenReturn(sessionId);
        when(validationManager.validate(any())).thenAnswer(ignore -> {
            var validationResult = new ValidationResult();
            validationResult.setValid(true);
            return validationResult;
        });

        // Act
        var sessionResult = Session.create(sessionIdGenerator, customerId, sessionDurationLimit, validationManager);

        // Assert
        assertTrue(sessionResult.isSuccess());
        assertNotNull(sessionResult.getValue());
    }

    @Test
    void createWithNotValidDataTest() {
        // Arrange
        var sessionId = new SessionId(UUID.randomUUID());
        var customerId = new CustomerId(UUID.randomUUID());
        var sessionDurationLimit = Duration.ofHours(1);

        when(sessionIdGenerator.generate()).thenReturn(sessionId);
        when(validationManager.validate(any())).thenAnswer(ignore -> {
            var validationResult = new ValidationResult();
            validationResult.setValid(false);
            validationResult.setErrors(List.of("Error"));
            return validationResult;
        });

        // Act
        var sessionResult = Session.create(sessionIdGenerator, customerId, sessionDurationLimit, validationManager);

        // Assert
        assertFalse(sessionResult.isSuccess());
        assertNotNull(sessionResult.getErrors());
    }

    @Test
    void loadValidSessionFromDb() {
        // Arrange
        var id = new SessionId(UUID.randomUUID());
        var customerId = new CustomerId(UUID.randomUUID());
        var expiresAt = LocalDateTime.now();

        // Act
        var session = Session.loadFromDb(id, customerId, expiresAt, Session.Status.ACTIVE);

        // Assert
        assertNotNull(session);
    }

    @Test
    void loadNotValidSessionFromDb() {
        // Arrange
        var id = new SessionId(UUID.randomUUID());
        var customerId = new CustomerId(UUID.randomUUID());
        var expiresAt = LocalDateTime.now();

        // Act Assert
        assertThrows(NullPointerException.class, () -> Session.loadFromDb(null, customerId, expiresAt, Session.Status.ACTIVE));
        assertThrows(NullPointerException.class, () -> Session.loadFromDb(id, null, expiresAt, Session.Status.ACTIVE));
        assertThrows(NullPointerException.class, () -> Session.loadFromDb(id, customerId, null, Session.Status.ACTIVE));
        assertThrows(NullPointerException.class, () -> Session.loadFromDb(id, customerId, expiresAt, null));
    }
}