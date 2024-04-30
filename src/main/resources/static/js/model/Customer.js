class Customer {
    constructor(authenticationData) {
        this._customerLogin = authenticationData.customerLogin;
        this._isAuthenticated = authenticationData.authenticated;
        console.log('Customer was created')
    }

    getLogin() {
        return this._customerLogin;
    }

    isAuthenticated() {
        return this._isAuthenticated;
    }
}

export default Customer;
