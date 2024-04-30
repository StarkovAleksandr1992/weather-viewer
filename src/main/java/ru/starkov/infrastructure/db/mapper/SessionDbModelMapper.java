package ru.starkov.infrastructure.db.mapper;

import org.mapstruct.Mapper;
import ru.starkov.domain.entity.Session;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.SessionId;
import ru.starkov.infrastructure.db.model.SessionDbModel;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class SessionDbModelMapper {


    public Session toEntity(SessionDbModel sessionDbModel) {
        var id = new SessionId(sessionDbModel.getId());
        var customerId = new CustomerId(sessionDbModel.getCustomerId());
        var expiresAt = sessionDbModel.getExpiresAt();
        var status = sessionDbModel.getStatus();
        return Session.loadFromDb(id, customerId, expiresAt, Session.Status.valueOf(status));
    }

    public UUID map(SessionId value) {
        return value.id();
    }

    public UUID map(CustomerId value) {
        return value.id();
    }

    public abstract SessionDbModel toModel(Session session);

    public abstract List<SessionDbModel> toCollectionModel(List<Session> sessions);

    public abstract List<Session> toCollectionEntity(List<SessionDbModel> sessions);
}
