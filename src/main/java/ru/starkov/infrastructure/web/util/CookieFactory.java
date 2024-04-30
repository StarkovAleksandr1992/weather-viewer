package ru.starkov.infrastructure.web.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieFactory {

    public Cookie createSessionCookie(String name, String value, int expiry) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        return cookie;
    }
}
