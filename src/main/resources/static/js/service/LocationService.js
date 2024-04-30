import Location from "../model/Location.js";

class LocationService {
    constructor() {
    }

    async findLocationsByName(query) {
        try {
            const response = await fetch('/locations/find?query=' + query, {
                method: 'GET',
                credentials: 'same-origin'
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            let locations = [];
            let locationsData = await response.json();
            locationsData.forEach(locationData => {
                let location = new Location(
                    locationData.locationName,
                    locationData.countryCode,
                    locationData.latitude,
                    locationData.longitude);
                locations.push(location);
            });
            return locations;
        } catch (error) {
            console.error('Error:', error);
            // Возвращаем reject для обещания в случае ошибки
            throw error;
        }
    }

    async saveLocation(location) {
        try {
            const requestData = {
                latitude: location.getLatitude(),
                longitude: location.getLongitude(),
                locationName: location.getName(),
                countryCode: location.getCountryCode()
            };

            return fetch('/locations/save', {
                method: 'post',
                body: JSON.stringify(requestData),
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'same-origin'
            });
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }

    async removeLocation(latitude, longitude) {
        try {
            let response = await fetch(`/locations/remove?lat=${latitude}&lon=${longitude}`, {
                method: 'DELETE',
                credentials: 'same-origin'
            });

            if (response.ok) {
                return location; // Return null if successful
            } else {
                const errorMessage = await response.text();
                throw new Error(errorMessage);
            }
        } catch (error) {
            console.error('Error removing location:', error);
            throw error;
        }
    }

    async getSavedLocations() {
        try {
            let response = await fetch('locations/saved', {
                method: 'get',
                credentials: 'same-origin'
            });
            let locationsData = await response.json();
            let locations = [];
            locationsData.forEach(location => {
                locations.push(new Location(location.locationName, location.countryCode, location.latitude, location.longitude));
            });
            return locations;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }
}

export default LocationService;