package ru.starkov.infrastructure.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.starkov.application.dto.SessionInfo;
import ru.starkov.application.usecase.auth.CheckCustomerAuthenticationStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static ru.starkov.infrastructure.web.util.Constants.*;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final CheckCustomerAuthenticationStatus checkCustomerAuthenticationStatus;

    @SuppressWarnings("all")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var cookies = request.getCookies();
            if (cookies != null) {
                Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equalsIgnoreCase(SESSION_ID_COOKIE_NAME))
                        .map(jakarta.servlet.http.Cookie::getValue)
                        .findAny()
                        .ifPresentOrElse(sessionId -> {
                                    request.setAttribute(SESSION_ID_ATTRIBUTE, sessionId);
                                    checkCustomerAuthenticationStatus.check(new SessionInfo(UUID.fromString(sessionId)))
                                            .onSuccess(result -> {
                                                request.setAttribute(CUSTOMER_ID_ATTRIBUTE, result.customerId());
                                                request.setAttribute(LOGIN_ATTRIBUTE, result.login());
                                                request.setAttribute(AUTHENTICATION_ATTRIBUTE, result.isAuthenticated());
                                            })
                                            .onFail(result -> request.setAttribute(AUTHENTICATION_ATTRIBUTE, false));
                                },
                                () -> request.setAttribute(AUTHENTICATION_ATTRIBUTE, false));
            } else {
                request.setAttribute(AUTHENTICATION_ATTRIBUTE, false);
            }
        } catch (Exception e) {
            request.setAttribute(AUTHENTICATION_ATTRIBUTE, false);
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
