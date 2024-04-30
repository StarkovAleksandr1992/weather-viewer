package ru.starkov.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.LocationId;
import ru.starkov.domain.identifier.LocationIdGenerator;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.domain.spi.validation.ValidationResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationTest {

    //Arrange
    //Act
    //Assert

    @Mock
    LocationIdGenerator locationIdGenerator;
    @Mock
    ValidationManager validationManager;

    @Test
    void createWithValidDataTest() {
        //Arrange
        when(locationIdGenerator.generate()).thenReturn(new LocationId(UUID.randomUUID()));
        when(validationManager.validate(any())).thenAnswer(ignore -> {
            var validationResult = new ValidationResult();
            validationResult.setValid(true);
            return validationResult;
        });
        var customerId = mock(CustomerId.class);
        //Act
        var locationListResult = Location.create(
                locationIdGenerator,
                customerId,
                "Almaty",
                "KZ",
                43.12,
                76.54,
                validationManager);
        //Assert
        assertTrue(locationListResult.isSuccess());
        assertNotNull(locationListResult.getValue());
        assertNull(locationListResult.getErrors());
    }

    @Test
    void createWithInvalidDataTest() {
        //Arrange
        when(locationIdGenerator.generate()).thenReturn(new LocationId(UUID.randomUUID()));
        when(validationManager.validate(any())).thenAnswer(ignore -> {
            var validationResult = new ValidationResult();
            validationResult.setValid(false);
            validationResult.setErrors(List.of("Какая-то ошибка валидации"));
            return validationResult;
        });
        var customerId = mock(CustomerId.class);
        //Act
        var locationListResult = Location.create(
                locationIdGenerator,
                customerId,
                "Almaty",
                "KZ",
                43.12,
                76.54,
                validationManager);
        //Assert
        assertFalse(locationListResult.isSuccess());
        assertNull(locationListResult.getValue());
        assertNotNull(locationListResult.getErrors());
    }

    @Test
    void loadFromDbWithValidData() {
        //Arrange
        var locationId = mock(LocationId.class);
        var customerId = mock((CustomerId.class));
        //Act
        var location = Location.loadFromDb(
                locationId,
                customerId,
                "Almaty",
                "kz",
                0,
                0);
        //Assert
        assertNotNull(locationId);
    }

    @Test
    void loadFromDbWithInvalidData() {
        //Arrange
        var locationId = mock(LocationId.class);
        var customerId = mock((CustomerId.class));
        //Act
        Executable loadFromDb = () -> Location.loadFromDb(
                locationId,
                customerId,
                null,
                "kz",
                0,
                0);
        //Assert
        assertThrows(NullPointerException.class, loadFromDb);

    }


}