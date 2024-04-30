package ru.starkov.application.usecase.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.RegistrationRequest;
import ru.starkov.application.dto.response.RegistrationResponse;
import ru.starkov.application.port.CustomerGateway;
import ru.starkov.application.usecase.auth.RegistrationUseCase;
import ru.starkov.domain.entity.Customer;
import ru.starkov.domain.identifier.CustomerIdGenerator;
import ru.starkov.domain.spi.security.PasswordEncoder;
import ru.starkov.domain.spi.validation.ValidationManager;
import ru.starkov.shared.common.Result;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationUseCaseImpl implements RegistrationUseCase {

    private final CustomerIdGenerator idGenerator;
    private final CustomerGateway customerGateway;
    private final PasswordEncoder passwordEncoder;
    private final ValidationManager validationManager;

    @Transactional
    @CacheEvict(value = "customers")
    @Override
    public Result<RegistrationResponse, List<String>> execute(RegistrationRequest registrationRequest) {
        List<String> errors = new ArrayList<>();

        var customerResult = Customer.create(
                        idGenerator,
                        registrationRequest.login(),
                        registrationRequest.password(),
                        registrationRequest.repeatedPassword(),
                        validationManager,
                        passwordEncoder)
                .onSuccess(customerGateway::save)
                .onFail(errors::addAll);

        if (errors.isEmpty()) {
            return Result.success(new RegistrationResponse(customerResult.getValue().getLogin().getStringValue()));
        }
        return Result.fail(errors);
    }
}
