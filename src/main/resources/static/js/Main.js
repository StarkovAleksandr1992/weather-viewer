import Customer from "./model/Customer.js";
import FoundWeatherContainer from "./component/FoundWeatherContainer.js";
import Header from "./component/Header.js";
import LocationsContainer from "./component/LocationsContainer.js";
import LoginForm from "./component/LoginForm.js";
import WeatherContainerGrid from "./component/WeatherContainerGrid.js";
import AuthenticationService from "./service/AuthenticationService.js";
import LocationService from "./service/LocationService.js";
import WeatherService from "./service/WeatherService.js";

document.addEventListener('DOMContentLoaded', () => {

    let services = {
        authenticationService: new AuthenticationService(),
        locationService: new LocationService(),
        weatherService: new WeatherService()
    }

    let dataContainers = {
        header: new Header(),
        loginForm: new LoginForm(),
        foundWeatherContainer: new FoundWeatherContainer(),
        weatherContainerGrid: new WeatherContainerGrid()
    }

    initialize()
        .then(customer => {
            displaySavedLocationWeatherData(customer);
            setupEventListeners(customer);
        });

    async function initialize() {
        let authenticationData = await services.authenticationService.fetchAuthenticationStatus();
        let customer = new Customer(authenticationData);
        let buttonContainer = document.getElementById('button-container');

        await displayHeader(customer, buttonContainer);
        return customer;
    }

    async function displayHeader(customer, buttonContainer) {
        if (customer.isAuthenticated()) {
            await renderAuthenticatedHeader(customer, buttonContainer);
        } else {
            await renderUnauthenticatedHeader(customer, buttonContainer);
        }
    }

    async function displaySavedLocationWeatherData(customer) {
        if (customer.isAuthenticated()) {
            let locations = await services.locationService.getSavedLocations();
            let weatherDataList = [];

            // Получение данных о погоде для каждого местоположения
            await Promise.all(locations.map(async (location) => {
                const weatherData = await services.weatherService.getWeatherDataByLocation(location);
                weatherDataList.push(weatherData);
            }));

            let weatherCards = [];

            // Генерация HTML для каждого объекта данных о погоде
            await Promise.all(weatherDataList.map(async (weatherData) => {
                const cardHtml = await dataContainers.weatherContainerGrid.generateWeatherCardHtml(weatherData);
                weatherCards.push(cardHtml);
            }));

            // Отображение сгенерированных карточек погоды
            dataContainers.weatherContainerGrid.displayGrid(weatherCards)
            dataContainers.weatherContainerGrid.attachCloseButtonHandler(services.locationService.removeLocation);
        }
    }


    async function setupEventListeners(customer) {
        let signInButton = document.getElementById('sign-in-button');
        let signUpButton = document.getElementById('sign-up-button');
        signInButton.addEventListener('click', await signIn);
        signUpButton.addEventListener('click', await signUp);

        let foundLocationsContainer = new LocationsContainer();
        let searchLocationInput = document.getElementById('location-search');
        let timeout;

        searchLocationInput.addEventListener('input', function () {
                clearTimeout(timeout);
                let query = searchLocationInput.value;
                timeout = setTimeout(async function () {
                    if (query.length > 0) {
                        try {
                            let locations = await services.locationService.findLocationsByName(query);
                            await foundLocationsContainer.displayFoundLocations(locations);
                            await foundLocationsContainer.setOnLocationSelected(async (selectedLocation) => {
                                let weatherData = await services.weatherService.getWeatherDataByLocation(selectedLocation);
                                await dataContainers.foundWeatherContainer.displayWeather(weatherData, customer);
                                await dataContainers.foundWeatherContainer.setOnAddToWatchListClicked(async (weatherData) => {
                                    await dataContainers.foundWeatherContainer.removeFoundWeatherContainer();
                                    await services.locationService.saveLocation(selectedLocation);
                                    if (weatherData) {
                                        let weatherCard = await dataContainers.weatherContainerGrid.generateWeatherCardHtml(weatherData);
                                        dataContainers.weatherContainerGrid.displayGrid([weatherCard]);
                                        dataContainers.weatherContainerGrid.attachCloseButtonHandler(services.locationService.removeLocation);
                                    }
                                });
                            });
                        } catch
                            (error) {
                            console.error('Error fetching locations:', error);
                        }
                    } else {
                        searchLocationInput.value = '';
                        foundLocationsContainer.clearResults();
                    }
                }, 500);
            }
        )
        ;
    }

    async function renderAuthenticatedHeader(customer, buttonContainer) {
        let customerLoginDiv = await dataContainers.header.createCustomerLoginDiv(customer.getLogin());
        let logoutButton = await dataContainers.header.createLogoutButton();
        logoutButton.onclick = services.authenticationService.logout;
        buttonContainer.appendChild(customerLoginDiv);
        buttonContainer.appendChild(logoutButton);
    }

    async function renderUnauthenticatedHeader(customer, buttonContainer) {
        let loginButton = await dataContainers.header.createLoginButton();
        loginButton.onclick = dataContainers.loginForm.toggleLoginForm;
        buttonContainer.appendChild(loginButton);
    }

    async function signIn() {
        let errorMessage = document.getElementById('error-message');
        let response = await services.authenticationService.login();
        if (response.ok) {
            window.location.reload();
        } else {
            await response.json().then(data => {
                errorMessage.textContent = data;
                errorMessage.style.display = "block";
            })
        }
    }

    async function signUp() {
        dataContainers.loginForm.redirectToRegistrationPage();
    }
})