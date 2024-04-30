class WeatherContainerGrid {

    constructor() {
    }


    // Метод для генерации HTML-разметки карточек погоды из массива данных
    async generateWeatherCardHtml(weatherData) {
        let locationName = weatherData.getLocationName();
        let countryCode = weatherData.getCountryCode();
        let temperature = weatherData.getTemperature();
        let maxTemperature = weatherData.getMaxTemperature();
        let minTemperature = weatherData.getMinTemperature();
        let iconUrl = 'https://openweathermap.org/img/wn/' + weatherData.getIconUrl() + '@2x.png';
        let description = weatherData.getDescription();
        let latitude = weatherData.getLatitude();
        let longitude = weatherData.getLongitude();

        return `<div class="weather-card">
                <span class="latitude" style="display: none">${latitude}</span>
                <span class="longitude" style="display: none">${longitude}</span>
                <button class="close-btn" type="button">×</button>
                    <div class="card-location">${locationName}<span id="country-code">${countryCode}</span></div>
                    <div class="card-weather">
                        <div class="card-current-temperature-value">${temperature}<sup id="degree-symbol">°</sup><span id="celsius-symbol">C</span></div>
                        <div class="card-day-temperature-values">
                            <div class="card-day-max-temperature-value">max: ${maxTemperature}<sup>°</sup><span>C</span></div>
                            <div class="card-day-min-temperature-value">min: ${minTemperature}<sup>°</sup><span>C</span></div>
                        </div>
                        <img class="card-weather-icon" src="${iconUrl}" alt="weather icon">
                    </div>
                    <div class="description-container">
                        <div class="card-weather-description">${description}</div>
                    </div>
                </div>`;
    }


    displayGrid(weatherCards) {
        let grid = document.getElementById('weather-data-container');


        // Получить текущие карточки погоды из сетки
        let existingCards = Array.from(grid.querySelectorAll('.weather-card'));
        existingCards = existingCards.map(card => card.outerHTML);
        let allCards = [];
        weatherCards.forEach(card => {
            if (!this._contains(existingCards, card)) {
                allCards.push(card);
            }
        });
        allCards = allCards.concat(existingCards);
        allCards = allCards.sort((a, b) => {
            let locationNameA = this._getLocationNameFromCard(a);
            let locationNameB = this._getLocationNameFromCard(b);
            return locationNameB.localeCompare(locationNameA);
        });

        // Удалить все текущие элементы из сетки
        while (grid.lastElementChild) {
            grid.removeChild(grid.lastElementChild);
        }

        // Вставить отсортированные карточки погоды в сетку
        allCards.forEach(card => {
            grid.insertAdjacentHTML('afterbegin', card);
        });
    }

    attachCloseButtonHandler(func) {
        const grid = document.getElementById('weather-data-container');

        grid.addEventListener('click', function (event) {
            if (event.target.classList.contains('close-btn')) {
                // Если кликнули на кнопку закрытия, удаляем соответствующую карточку
                let card = event.target.closest('.weather-card');
                card.remove();
                let latitude = card.querySelector('span.latitude').textContent;
                let longitude = card.querySelector('span.longitude').textContent;
                func(latitude, longitude);
            }
        });
    }

    _getLocationNameFromCard(cardHtml) {
        const regex = /<div class="card-location">(.*?)<span/;
        let match = cardHtml.match(regex);
        if (match && match.length > 1) {
            return match[1];
        }
    }

    _getCountryCodeFromCard(cardHtml) {
        const regex = /<span id="country-code">(.*?)<\/span>/;
        let match = cardHtml.match(regex);
        if (match && match.length > 1) {
            return match[1];
        }
    }

    _contains(existingCards, addedCard) {
        for (let i = 0; i < existingCards.length; i++) {
            let locationName = this._getLocationNameFromCard(existingCards[i]);
            let countryCode = this._getCountryCodeFromCard(existingCards[i]);

            if (locationName === this._getLocationNameFromCard(addedCard) &&
                countryCode === this._getCountryCodeFromCard(addedCard)) {
                return true;
            }
        }
        return false;
    }
}

export default WeatherContainerGrid;
