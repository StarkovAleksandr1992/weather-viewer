package ru.starkov.application.usecase.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starkov.application.dto.request.LoginRequest;
import ru.starkov.application.dto.response.LoginResponse;
import ru.starkov.application.port.CustomerGateway;
import ru.starkov.application.usecase.auth.LoginUseCase;
import ru.starkov.domain.spi.security.PasswordVerifier;
import ru.starkov.shared.common.Result;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final CustomerGateway customerGateway;
    private final PasswordVerifier passwordVerifier;

    @Transactional(readOnly = true)
    @Override
    public Result<LoginResponse, List<String>> execute(LoginRequest request) {
        List<String> errors = new ArrayList<>();
        var customerOptional = customerGateway.findByLogin(request.login());
        if (customerOptional.isEmpty()) {
            errors.add("Wrong login or password");
            return Result.fail(errors);
        }
        var customer = customerOptional.get();
        if (!customer.checkCredentials(passwordVerifier, request.password())) {
            errors.add("Wrong login or password");
            return Result.fail(errors);
        }
        return Result.success(new LoginResponse(customer.getId().id()));
    }
}
