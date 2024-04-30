package ru.starkov.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import ru.starkov.application.dto.request.RegistrationRequest;
import ru.starkov.infrastructure.web.payload.RegistrationPayload;

@Mapper(componentModel = "spring")
public interface RegistrationPayloadMapper {

    RegistrationRequest mapToRegistrationRequest(RegistrationPayload payload);

}
