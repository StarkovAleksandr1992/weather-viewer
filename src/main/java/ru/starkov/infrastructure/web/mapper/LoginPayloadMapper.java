package ru.starkov.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import ru.starkov.application.dto.request.LoginRequest;
import ru.starkov.infrastructure.web.payload.LoginPayload;

@Mapper(componentModel = "spring")
public interface LoginPayloadMapper {

    LoginRequest mapToLoginRequest(LoginPayload payload);

}

