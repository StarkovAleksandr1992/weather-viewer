package ru.starkov.domain.spi.validation;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ValidationResult {
    private boolean isValid;
    private List<String> errors;

}
