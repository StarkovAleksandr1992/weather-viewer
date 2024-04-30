package ru.starkov.domain.identifier;

@FunctionalInterface
public interface SessionIdGenerator {
    SessionId generate();
}
