package ru.starkov.infrastructure.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.starkov.infrastructure.db.model.CustomerDbModel;

import java.util.Optional;
import java.util.UUID;

public interface CustomerDao extends JpaRepository<CustomerDbModel, UUID> {

    Optional<CustomerDbModel> findByLogin(String login);
}
