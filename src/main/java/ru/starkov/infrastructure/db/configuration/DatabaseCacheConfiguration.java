package ru.starkov.infrastructure.db.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.NamedCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class DatabaseCacheConfiguration {

    @Bean
    @Primary
    public CacheManager databaseCacheManager() {
        return new ConcurrentMapCacheManager("customers", "sessions", "locations");
    }

    @CacheEvict(value = {"customers"}, allEntries = true)
    @Scheduled(fixedRateString = "${caching.customers_ttl_minutes}", timeUnit = TimeUnit.MINUTES)
    public void emptyCustomersCache() {
    }

    @Bean
    public CacheResolver cacheResolver() {
        return new NamedCacheResolver(databaseCacheManager(), "customers", "sessions", "locations");
    }
}
