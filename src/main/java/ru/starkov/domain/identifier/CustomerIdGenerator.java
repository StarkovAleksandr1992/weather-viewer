package ru.starkov.domain.identifier;

@FunctionalInterface
public interface CustomerIdGenerator {
    CustomerId generate();
}
