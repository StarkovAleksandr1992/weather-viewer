package ru.starkov.infrastructure.web.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class WebServicesCacheConfiguration {

    @Bean(name = "webServicesCacheManager")
    public CacheManager webServicesCacheManager() {
        return new ConcurrentMapCacheManager("locations", "weather");
    }

    @CacheEvict(value = "locations", allEntries = true)
    @Scheduled(fixedRateString = "${caching.locations_list_ttl_minutes}", timeUnit = TimeUnit.MINUTES)
    public void emptyLocationsCache() {
    }


    @CacheEvict(value = "weather", allEntries = true)
    @Scheduled(fixedRateString = "${caching.weather_data_ttl_minutes}", timeUnit = TimeUnit.MINUTES)
    public void emptyWeatherCache() {
    }
}
