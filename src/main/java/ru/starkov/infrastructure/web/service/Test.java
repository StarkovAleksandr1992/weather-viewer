package ru.starkov.infrastructure.web.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.starkov.infrastructure.web.service.dto.SearchLocationResponseData;
import ru.starkov.infrastructure.web.service.dto.WeatherResponseData;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        String api = "fcde6b3179be87f5711da3474963580f";

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=33.44&lon=-94.04&appid=%s".formatted(api);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> forEntity2 = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity2.getBody());


        ResponseEntity<WeatherResponseData> forEntity1 = restTemplate.getForEntity(url, WeatherResponseData.class);
        System.out.println(forEntity1.getBody());

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        String urlGeo = "http://api.openweathermap.org/geo/1.0/direct?q=leon&limit=10&appid=%s".formatted(api);
        ResponseEntity<SearchLocationResponseData[]> forEntity = restTemplate.getForEntity(urlGeo, SearchLocationResponseData[].class);
        Arrays.stream(forEntity.getBody()).forEach(System.out::println);

    }
}
