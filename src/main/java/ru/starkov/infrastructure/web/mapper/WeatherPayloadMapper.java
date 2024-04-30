package ru.starkov.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.starkov.infrastructure.web.payload.WeatherPayload;
import ru.starkov.infrastructure.web.service.dto.WeatherResponseData;

@Mapper(componentModel = "spring")
public interface WeatherPayloadMapper {

    @Mapping(target = "description", expression = "java(weatherResponseData.getWeatherInfos() != null && weatherResponseData.getWeatherInfos().length > 0 ? weatherResponseData.getWeatherInfos()[0].getMain() : null)")
    @Mapping(target = "detailedDescription", expression = "java(weatherResponseData.getWeatherInfos() != null && weatherResponseData.getWeatherInfos().length > 0 ? weatherResponseData.getWeatherInfos()[0].getDescription() : null)")
    @Mapping(target = "iconUrl", expression = "java(weatherResponseData.getWeatherInfos() != null && weatherResponseData.getWeatherInfos().length > 0 ? weatherResponseData.getWeatherInfos()[0].getIcon() : null)")
    @Mapping(source = "weatherParameters.temperature", target = "temperature")
    @Mapping(source = "weatherParameters.feelsLikeTemperature", target = "feelsLikeTemperature")
    @Mapping(source = "weatherParameters.minTemperature", target = "minTemperature")
    @Mapping(source = "weatherParameters.maxTemperature", target = "maxTemperature")
    @Mapping(source = "weatherParameters.pressure", target = "pressure")
    @Mapping(source = "weatherParameters.humidity", target = "humidity")
    WeatherPayload mapToWeatherPayload(WeatherResponseData weatherResponseData);


}
