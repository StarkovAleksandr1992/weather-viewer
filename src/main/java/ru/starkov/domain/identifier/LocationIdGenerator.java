package ru.starkov.domain.identifier;

@FunctionalInterface
public interface LocationIdGenerator {
    LocationId generate();
}
