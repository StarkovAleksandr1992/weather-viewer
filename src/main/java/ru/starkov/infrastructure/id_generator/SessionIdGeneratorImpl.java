package ru.starkov.infrastructure.id_generator;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;
import ru.starkov.domain.identifier.SessionId;
import ru.starkov.domain.identifier.SessionIdGenerator;

@Component
public class SessionIdGeneratorImpl implements SessionIdGenerator {
    @Override
    public SessionId generate() {
        return new SessionId(UuidCreator.getTimeBased());
    }
}
