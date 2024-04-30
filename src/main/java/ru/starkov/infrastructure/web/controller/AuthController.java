package ru.starkov.infrastructure.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.starkov.application.dto.request.LogoutRequest;
import ru.starkov.application.dto.request.SessionCreationRequest;
import ru.starkov.application.usecase.auth.CreateSessionUseCase;
import ru.starkov.application.usecase.auth.LoginUseCase;
import ru.starkov.application.usecase.auth.LogoutUseCase;
import ru.starkov.application.usecase.auth.RegistrationUseCase;
import ru.starkov.infrastructure.web.mapper.LoginPayloadMapper;
import ru.starkov.infrastructure.web.mapper.RegistrationPayloadMapper;
import ru.starkov.infrastructure.web.payload.AuthenticationPayload;
import ru.starkov.infrastructure.web.payload.LoginPayload;
import ru.starkov.infrastructure.web.payload.RegistrationPayload;
import ru.starkov.infrastructure.web.util.CookieFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.starkov.infrastructure.web.util.Constants.*;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUsecase;
    private final LogoutUseCase logoutUseCase;
    private final CreateSessionUseCase createSessionUseCase;
    private final RegistrationUseCase registrationUseCase;
    private final CookieFactory cookieFactory;
    private final LoginPayloadMapper loginPayloadMapper;
    private final RegistrationPayloadMapper registrationPayloadMapper;


    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginPayload loginPayload,
                                   HttpServletResponse response) {
        List<String> responseErrors = new ArrayList<>();
        loginUsecase.execute(loginPayloadMapper.mapToLoginRequest(loginPayload))
                .onSuccess(customerLoginResponse -> createSessionUseCase
                        .create(new SessionCreationRequest(customerLoginResponse.customerId()))
                        .onSuccess(sessionCreationResponse -> response.addCookie(cookieFactory.createSessionCookie(
                                SESSION_ID_COOKIE_NAME,
                                sessionCreationResponse.sessionId().toString(),
                                sessionCreationResponse.expiry())))
                        .onFail(responseErrors::addAll))
                .onFail(responseErrors::addAll);
        if (!responseErrors.isEmpty()) {
            return new ResponseEntity<>(responseErrors, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestAttribute(SESSION_ID_ATTRIBUTE) String sessionId,
                                    @RequestAttribute(CUSTOMER_ID_ATTRIBUTE) String customerId,
                                    HttpServletResponse response) {
        List<String> responseErrors = new ArrayList<>();
        logoutUseCase.execute(new LogoutRequest(UUID.fromString(customerId), UUID.fromString(sessionId)))
                .onSuccess(logoutResponse -> response.addCookie(cookieFactory.createSessionCookie(
                        SESSION_ID_COOKIE_NAME,
                        logoutResponse.sessionId().toString(),
                        logoutResponse.expiry())))
                .onFail(responseErrors::addAll);
        if (!responseErrors.isEmpty()) {
            return new ResponseEntity<>(responseErrors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(null);
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String registerCustomerAccount(RegistrationPayload registrationPayload, Model model, HttpSession session) {
        var result = registrationUseCase.execute(registrationPayloadMapper.mapToRegistrationRequest(registrationPayload));
        result
                .onSuccess(registrationResponse -> session.setAttribute("login", registrationResponse.login()))
                .onFail(errors -> {
                    model.addAttribute("login", registrationPayload.login());
                    model.addAttribute("errors", errors);
                });

        if (result.isSuccess()) {
            return "redirect:/";
        } else {
            return "registration";
        }
    }

    @GetMapping("/authentication-status")
    @ResponseBody
    public ResponseEntity<?> isAuthenticated(@RequestAttribute(name = AUTHENTICATION_ATTRIBUTE) Boolean isAuthenticated,
                                             @RequestAttribute(name = LOGIN_ATTRIBUTE, required = false) String login) {
        if (isAuthenticated) {
            return ResponseEntity.ok(new AuthenticationPayload(true, login));
        } else {
            return ResponseEntity.ok(new AuthenticationPayload(false, "guest"));
        }
    }
}
