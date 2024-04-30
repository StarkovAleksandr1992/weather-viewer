package ru.starkov.infrastructure.db.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ru.starkov.application.port.LocationGateway;
import ru.starkov.domain.entity.Location;
import ru.starkov.infrastructure.db.dao.LocationDao;
import ru.starkov.infrastructure.db.mapper.LocationDbModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LocationRepository implements LocationGateway {

    private final LocationDao locationDao;
    private final LocationDbModelMapper mapper;

    @CacheEvict(value = "locations", allEntries = true)
    @Override
    public void save(Location location) {
        locationDao.save(mapper.toModel(location));
    }

    @CacheEvict(value = "locations", allEntries = true)
    @Override
    public void delete(Location location) {
        locationDao.delete(mapper.toModel(location));
    }

    @Override
    @Cacheable(value = "locations", key = "#p0", condition = "#p0!=null")
    public List<Location> findByCustomerId(UUID customerId) {
        return mapper.toCollectionEntity(locationDao.findAllByCustomerId(customerId));
    }

    @Override
    public long countAllLocationsByCustomerId(UUID customerId) {
        return locationDao.countAllByCustomerId(customerId);
    }

    @Override
    public Optional<Location> findByCustomerIdAndCoordinates(UUID customerId, double latitude, double longitude) {
        return locationDao.findByCustomerIdAndLatitudeAndLongitude(customerId, latitude, longitude)
                .map(mapper::toEntity);
    }
}
