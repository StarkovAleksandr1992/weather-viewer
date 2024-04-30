package ru.starkov.domain.spi.validation;

import ru.starkov.domain.entity.Location;

public interface LocationValidator {

    ValidationResult validate(Location location);
}
