package ru.starkov.infrastructure.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.starkov.infrastructure.db.model.LocationDbModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationDao extends JpaRepository<LocationDbModel, UUID> {

    List<LocationDbModel> findAllByCustomerId(UUID customerId);

    Optional<LocationDbModel> findByCustomerIdAndLatitudeAndLongitude(
            UUID customerId,
            double latitude,
            double longitude);

    long countAllByCustomerId(UUID customerId);

}
