package ru.starkov.infrastructure.db.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ru.starkov.application.port.SessionGateway;
import ru.starkov.domain.entity.Session;
import ru.starkov.infrastructure.db.dao.SessionDao;
import ru.starkov.infrastructure.db.mapper.SessionDbModelMapper;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepository implements SessionGateway {


    private final SessionDao sessionDao;
    private final SessionDbModelMapper mapper;

    @Override
    @CacheEvict(value = "sessions", allEntries = true)
    public void save(Session session) {
        sessionDao.save(mapper.toModel(session));
    }

    @Override
    @CacheEvict("sessions")
    public void delete(UUID id) {
        sessionDao.deleteById(id);
    }

    @Override
    @Cacheable("sessions")
    public Optional<Session> findSessionById(UUID id) {
        return sessionDao.findById(id).map(mapper::toEntity);
    }

}
