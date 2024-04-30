package ru.starkov.infrastructure.web.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.starkov.infrastructure.web.filter.AuthFilter;

@Configuration
public class FilterConfiguration {

    @Bean
    @Order(1)
    public FilterRegistrationBean<AuthFilter> authenticationFilterBean(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>(authFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
