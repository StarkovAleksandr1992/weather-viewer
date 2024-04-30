class Header {

    constructor() {
        console.log('Header was created')
    }

    async createLoginButton() {
        let loginButton = document.createElement('button');
        loginButton.className = 'btn btn-success';
        loginButton.type = 'button';
        loginButton.id = 'login-button';
        loginButton.innerText = 'Login';
        let parentElement = document.getElementById('button-container');
        parentElement.appendChild(loginButton);
        return loginButton;
    }

    async createLogoutButton() {
        let logoutButton = document.createElement('button');
        logoutButton.className = 'btn btn-danger';
        logoutButton.type = 'button';
        logoutButton.id = 'logout-button';
        logoutButton.innerText = 'Logout';
        return logoutButton;
    }

    async createCustomerLoginDiv(customerLogin) {
        let userInfoDiv = document.createElement('div');
        userInfoDiv.style = 'padding-right: 5px;';
        userInfoDiv.innerText = customerLogin;
        return userInfoDiv;
    }
}

export default Header;
