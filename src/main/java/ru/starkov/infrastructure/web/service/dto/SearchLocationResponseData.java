package ru.starkov.infrastructure.web.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
public class SearchLocationResponseData {
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "lat")
    private double latitude;

    @JsonProperty(value = "lon")
    private double longitude;

    @JsonProperty(value = "country")
    private String country;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LocationGeoComparator implements Comparator<SearchLocationResponseData> {

        public static LocationGeoComparator getInstance() {
            return new LocationGeoComparator();
        }

        @Override
        public int compare(SearchLocationResponseData o1, SearchLocationResponseData o2) {
            double latitude1 = o1.getLatitude();
            double latitude2 = o2.getLatitude();

            double longitude1 = o1.getLongitude();
            double longitude2 = o2.getLongitude();

            double coordinateSum = Math.abs((latitude1 + longitude1) - (latitude2 + longitude2));

            // Если сумма разницы в координатах меньше 2, считаем объекты равными
            if (coordinateSum < 2) {
                return 0;
            } else if (coordinateSum > 2) {
                return 1; // объекты не равны
            } else {
                // В этом случае разница в координатах равна 2, поэтому нельзя точно определить порядок
                // Поэтому можно сравнить другие параметры объектов или вернуть разные значения для обеспечения уникальности
                // Например, можно сравнивать их идентификаторы или другие поля
                return String.CASE_INSENSITIVE_ORDER.compare(o1.name, o2.name);
            }
        }
    }

}
