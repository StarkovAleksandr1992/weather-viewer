package ru.starkov.infrastructure.id_generator;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.CustomerIdGenerator;

@Component
public class CustomerIdGeneratorImpl implements CustomerIdGenerator {
    @Override
    public CustomerId generate() {
        return new CustomerId(UuidCreator.getTimeBased());
    }
}
