package ru.starkov.application.port;

import ru.starkov.domain.entity.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationGateway {
    void save(Location location);

    void delete(Location location);

    List<Location> findByCustomerId(UUID customerId);

    long countAllLocationsByCustomerId(UUID customerId);

    Optional<Location> findByCustomerIdAndCoordinates(UUID customerId, double latitude, double longitude);

}
