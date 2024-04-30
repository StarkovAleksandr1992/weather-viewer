package ru.starkov.application.port;

import ru.starkov.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerGateway {

    void save(Customer customer);
    Optional<Customer> findById(UUID id);

    Optional<Customer> findByLogin(String login);
}
