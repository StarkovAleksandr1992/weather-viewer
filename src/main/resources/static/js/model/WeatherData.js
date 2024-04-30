class WeatherData {
    constructor(
        locationName,
        countryCode,
        latitude,
        longitude,
        description,
        detailedDescription,
        temperature,
        feelsLikeTemperature,
        minTemperature,
        maxTemperature,
        humidity,
        pressure,
        iconUrl
    ) {
        this._locationName = locationName;
        this._countryCode = countryCode;
        this._latitude = latitude;
        this._longitude = longitude;
        this._description = description;
        this._detailedDescription = detailedDescription;
        this._temperature = temperature;
        this._feelsLikeTemperature = feelsLikeTemperature;
        this._minTemperature = minTemperature;
        this._maxTemperature = maxTemperature;
        this._humidity = humidity;
        this._pressure = pressure;
        this._iconUrl = iconUrl;
    }


    getLocationName() {
        return this._locationName;
    }

    getCountryCode() {
        return this._countryCode;
    }

    getLatitude() {
        return this._latitude;
    }

    getLongitude() {
        return this._longitude;
    }

    getDescription() {
        return this._description;
    }

    getDetailedDescription() {
        return this._detailedDescription;
    }

    getFeelsLikeTemperature() {
        return this._feelsLikeTemperature;
    }

    getHumidity() {
        return this._humidity;
    }

    getIconUrl() {
        return this._iconUrl;
    }

    getMaxTemperature() {
        return this._maxTemperature;
    }

    getMinTemperature() {
        return this._minTemperature;
    }

    getPressure() {
        return this._pressure;
    }

    getTemperature() {
        return this._temperature;
    }
}

export default WeatherData;
