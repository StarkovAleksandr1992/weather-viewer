package ru.starkov.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.CustomerIdGenerator;
import ru.starkov.domain.spi.security.PasswordEncoder;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.domain.spi.validation.ValidationResult;
import ru.starkov.domain.value.Login;
import ru.starkov.domain.value.Password;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerTest {
    @Mock
    private CustomerIdGenerator customerIdGenerator;
    @Mock
    private ValidationManager validationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final String login = "Customer";
    private final char[] password = new char[]{};
    private final char[] repeatedPassword = new char[]{};


    @Test
    void createWithValidDataTest() {
        // Arrange
        when(customerIdGenerator.generate()).thenReturn(new CustomerId(UUID.randomUUID()));
        when(validationManager.validate(any())).thenAnswer(invocation -> {
            var validationResult = new ValidationResult();
            validationResult.setValid(true);
            return validationResult;
        });

        // Act
        var customerResult = Customer
                .create(customerIdGenerator, login, password, repeatedPassword, validationManager, passwordEncoder);

        // Assert
        assertTrue(customerResult.isSuccess());
        assertNotNull(customerResult.getValue());
    }

    @Test
    void createWithNotValidDataTest() {
        // Arrange
        when(customerIdGenerator.generate()).thenReturn(new CustomerId(UUID.randomUUID()));
        when(validationManager.validate(any())).thenAnswer(invocation -> {
            var validationResult = new ValidationResult();
            validationResult.setValid(false);
            validationResult.setErrors(List.of("Error"));
            return validationResult;
        });

        // Act
        var customerResult = Customer
                .create(customerIdGenerator, login, password, repeatedPassword, validationManager, passwordEncoder);

        // Assert
        assertFalse(customerResult.isSuccess());
        assertNotNull(customerResult.getErrors());
    }


    @Test
    void loadValidCustomerFromDb() {
        // Arrange
        var id = new CustomerId(UUID.randomUUID());
        var cLogin = Login.loadFromDb(login);
        var cPassword = Password.loadFromDb("asd");

        // Act
        var customer = Customer.loadFromDb(id, cLogin, cPassword);

        // Assert
        assertNotNull(customer);
    }

    @Test
    void loadNotValidCustomerFromDb() {
        // Arrange
        var id = new CustomerId(UUID.randomUUID());
        var cLogin = Login.loadFromDb(login);
        var cPassword = Password.loadFromDb("asd");

        // Act Assert
        assertThrows(NullPointerException.class, () -> Customer.loadFromDb(null, cLogin, cPassword));
        assertThrows(NullPointerException.class, () -> Customer.loadFromDb(id, null, cPassword));
        assertThrows(NullPointerException.class, () -> Customer.loadFromDb(id, cLogin, null));
    }
}