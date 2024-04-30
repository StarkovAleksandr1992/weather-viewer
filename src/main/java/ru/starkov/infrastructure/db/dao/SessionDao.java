package ru.starkov.infrastructure.db.dao;

import org.springframework.data.repository.CrudRepository;
import ru.starkov.infrastructure.db.model.SessionDbModel;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao extends CrudRepository<SessionDbModel, UUID> {
}
