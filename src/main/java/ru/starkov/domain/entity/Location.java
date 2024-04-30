package ru.starkov.domain.entity;

import lombok.*;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.LocationId;
import ru.starkov.domain.identifier.LocationIdGenerator;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.shared.common.Result;
import ru.starkov.shared.mark.Entity;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class Location implements Entity, Comparable<Location> {

    private final LocationId id;
    private final CustomerId customerId;
    private final String name;
    private final String countryCode;
    private final double latitude;
    private final double longitude;

    public static Result<Location, List<String>> create(
            LocationIdGenerator generator,
            CustomerId customerId,
            String name,
            String countryCode,
            double latitude,
            double longitude,
            ValidationManager validationManager) {

        var location = new Location(generator.generate(), customerId, name, countryCode, latitude, longitude);
        var validationResult = validationManager.validate(location);
        if (validationResult.isValid()) {
            return Result.success(location);
        }
        return Result.fail(validationResult.getErrors());
    }

    public static Location loadFromDb(
            LocationId id,
            CustomerId customerId,
            String name,
            String countryCode,
            double latitude,
            double longitude) {

        Objects.requireNonNull(id, "Location id cannot be null");
        Objects.requireNonNull(name, "Location locationName cannot be null");

        return new Location(id, customerId, name, countryCode, latitude, longitude);
    }


    @Override
    public int compareTo(Location o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    }
}
