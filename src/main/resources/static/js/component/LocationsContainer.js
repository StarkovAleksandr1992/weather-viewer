class LocationsContainer {

    constructor() {
        this._searchResultContainer = document.getElementById('search-results');
        this._searchLocationInput = document.getElementById('location-search');

        this.onLocationSelected = null;
    }

    async setOnLocationSelected(func) {
        this.onLocationSelected = func;
    }

    async displayFoundLocations(locations) {
        this.clearResults();
        locations.forEach((location) => {
            let foundLocationElement = document.createElement('li');
            foundLocationElement.className = 'list-group-item';
            foundLocationElement.textContent = `${location.getName()} (${location.getCountryCode()})`;
            foundLocationElement.onclick = () => {
                this.clearResults();
                if (this.onLocationSelected != null) {
                    this.onLocationSelected(location);
                    this._searchLocationInput.value = '';
                }
            }
            this._searchResultContainer.appendChild(foundLocationElement);
        });
        this._show(this._searchResultContainer);
    }

    clearResults() {
        while (this._searchResultContainer.firstChild) {
            this._searchResultContainer.removeChild(this._searchResultContainer.firstChild);
        }
        this._hide(this._searchResultContainer);
    }

    _show(element) {
        element.style.display = 'block';
    }

    _hide(element) {
        element.style.display = 'none';
    }

}

export default LocationsContainer;
