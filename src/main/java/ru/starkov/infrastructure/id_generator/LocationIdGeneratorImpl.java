package ru.starkov.infrastructure.id_generator;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;
import ru.starkov.domain.identifier.LocationId;
import ru.starkov.domain.identifier.LocationIdGenerator;

@Component
public class LocationIdGeneratorImpl implements LocationIdGenerator {
    @Override
    public LocationId generate() {
        return new LocationId(UuidCreator.getTimeBased());
    }
}
