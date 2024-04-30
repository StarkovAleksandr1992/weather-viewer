package ru.starkov.infrastructure.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {


    @Value("${security.bcrypt.cost_factor}")
    private int bcryptCostFactor;

    @Bean
    public BcryptPasswordEncoder bcryptPasswordEncoder() {
        if (bcryptCostFactor == 0) {
            return new BcryptPasswordEncoder(BCrypt.MIN_COST);
        }

        return new BcryptPasswordEncoder(this.bcryptCostFactor);
    }

}
