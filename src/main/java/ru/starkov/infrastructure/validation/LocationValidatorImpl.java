package ru.starkov.infrastructure.validation;

import org.springframework.stereotype.Component;
import ru.starkov.domain.entity.Location;
import ru.starkov.domain.spi.validation.LocationValidator;
import ru.starkov.domain.spi.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LocationValidatorImpl implements LocationValidator {

    private static final int MIN_LATITUDE = -90;
    private static final int MAX_LATITUDE = 90;
    private static final int MIN_LONGITUDE = -180;
    private static final int MAX_LONGITUDE = 180;

    @Override
    public ValidationResult validate(Location location) {
        var result = new ValidationResult();
        List<String> errors = new ArrayList<>();
        var customerId = location.getCustomerId();
        var name = location.getName();
        var countryCode = location.getCountryCode();
        var latitude = location.getLatitude();
        var longitude = location.getLongitude();

        if (Objects.isNull(customerId)) {
            errors.add("The customer ID cannot be empty when adding a location");
        }

        if (Objects.isNull(name) || name.isBlank()) {
            errors.add("Location name cannot be bull or blank");
        }

        if (Objects.isNull(countryCode) || countryCode.isBlank()) {
            errors.add("Location's country code cannot be null or blank");
        }

        if (!isLatitudeValid(latitude)) {
            errors.add("Latitude is not within valid range");
        }
        if (!isLongitudeValid(longitude)) {
            errors.add("Longitude is not within valid range");
        }

        result.setValid(errors.isEmpty());
        result.setErrors(errors);

        return result;
    }

    private static boolean isLatitudeValid(double latitude) {
        return latitude > MIN_LATITUDE &&
                latitude < MAX_LATITUDE;
    }

    private static boolean isLongitudeValid(double longitude) {
        return longitude > MIN_LONGITUDE && longitude < MAX_LONGITUDE;
    }
}
