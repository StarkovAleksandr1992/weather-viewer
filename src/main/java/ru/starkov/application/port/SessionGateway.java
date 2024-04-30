package ru.starkov.application.port;

import ru.starkov.domain.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionGateway {
    Optional<Session> findSessionById(UUID id);

    void save(Session session);

    void delete(UUID id);

}
