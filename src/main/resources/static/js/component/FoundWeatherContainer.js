class FoundWeatherContainer {
    constructor() {
        this._onAddToWatchListClicked = null;

    }

    async setOnAddToWatchListClicked(func) {
        this._onAddToWatchListClicked = func;
    }

    async displayWeather(weatherData, customer) {
        let isLoggedIn = customer && customer.isAuthenticated();

        let addToWatchlistButton = isLoggedIn ?
            `<button class="add-to-watchlist-btn" type="button">Add to watchlist</button>` :
            '';

        let weatherCardHtml = this.generateWeatherCardHtml(weatherData, addToWatchlistButton);

        this.displayWeatherCard(weatherCardHtml);

        this.setupCloseButtonListener();

        if (isLoggedIn) {
            this.setupAddToWatchlistButtonListener(weatherData);
        }
    }

    generateWeatherCardHtml(weatherData, addToWatchlistButton) {
        let locationName = weatherData.getLocationName();
        let countryCode = weatherData.getCountryCode();
        let temperature = weatherData.getTemperature();
        let maxTemperature = weatherData.getMaxTemperature();
        let minTemperature = weatherData.getMinTemperature();
        let iconUrl = 'https://openweathermap.org/img/wn/' + weatherData.getIconUrl() + '@2x.png';
        let description = weatherData.getDescription();

        return `<div class="weather-popup" id="weather-popup">
                    <button class="close-btn" type="button">×</button>
                    <div class="found-weather-card" id="found-weather-card">
                        <div class="card-location">${locationName}<span id="country-code">${countryCode}</span></div>
                        <div class="card-weather">
                            <div class="card-current-temperature-value">${temperature}<sup id="degree-symbol">°</sup><span
                                    id="celsius-symbol">C</span>
                            </div>
                            <div class="card-day-temperature-values">
                                <div class="card-day-max-temperature-value">max: ${maxTemperature}<sup>°</sup><span>C</span></div>
                                <div class="card-day-min-temperature-value">min: ${minTemperature}<sup>°</sup><span>C</span></div>
                            </div>
                            <img class="card-weather-icon" src="${iconUrl}" alt="weather icon">
                        </div>
                        <div class="description-container">
                            <div class="card-weather-description">${description}</div>
                            ${addToWatchlistButton}
                        </div>
                    </div>
                </div>`;
    }

    displayWeatherCard(weatherCardHtml) {
        const navBar = document.querySelector('.navbar');
        const weatherContainer = document.getElementById('weather-popup');
        if (weatherContainer != null) {
            weatherContainer.remove();
        }
        navBar.insertAdjacentHTML('afterend', weatherCardHtml);
    }

    setupCloseButtonListener() {
        const closeButton = document.querySelector('.close-btn');
        closeButton.addEventListener('click', () => {
            this.removeFoundWeatherContainer();
        });
    }

    setupAddToWatchlistButtonListener(weatherData) {
        const addToWatchlistBtn = document.querySelector('.add-to-watchlist-btn');
        const handleClick = () => {
            if (this._onAddToWatchListClicked != null) {
                this._onAddToWatchListClicked(weatherData);
            }
            addToWatchlistBtn.removeEventListener('click', handleClick);
        };
        addToWatchlistBtn.addEventListener('click', handleClick);
    }

    async removeFoundWeatherContainer() {
        const foundWeatherContainer = document.getElementById('weather-popup');
        foundWeatherContainer.parentElement.removeChild(foundWeatherContainer);
    }
}

export default FoundWeatherContainer;
