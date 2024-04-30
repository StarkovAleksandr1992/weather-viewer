class Location {
    constructor(name, countryCode, latitude, longitude) {
        this._name = name;
        this._countryCode = countryCode;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    getName() {
        return this._name;
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

}

export default Location;