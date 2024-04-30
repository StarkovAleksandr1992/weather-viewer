import WeatherData from "../model/WeatherData.js";

class WeatherService {
    async getWeatherDataByLocation(location) {
        try {
            let name = location.getName();
            let latitude = location.getLatitude();
            let longitude = location.getLongitude();
            let countryCode = location.getCountryCode();

            const response = await fetch(
                `http://localhost:8080/weather?lat=${latitude}&lon=${longitude}&name=${name}&code=${countryCode}`);
            if (!response.ok) {
                throw new Error('Failed to fetch weather data');
            }
            let rawWeatherData = await response.json();
            return new WeatherData(
                rawWeatherData.locationName,
                rawWeatherData.countryCode,
                rawWeatherData.latitude,
                rawWeatherData.longitude,
                rawWeatherData.description,
                rawWeatherData.detailedDescription,
                rawWeatherData.temperature,
                rawWeatherData.feelsLikeTemperature,
                rawWeatherData.minTemperature,
                rawWeatherData.maxTemperature,
                rawWeatherData.humidity,
                rawWeatherData.pressure,
                rawWeatherData.iconUrl);
        } catch (error) {
            console.error('Error fetching weather data:', error);
            return [];
        }
    }
}

export default WeatherService;
