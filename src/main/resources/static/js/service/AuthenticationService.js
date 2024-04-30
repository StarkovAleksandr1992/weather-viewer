class AuthenticationService {

    constructor() {
        console.log('Authentication service was created')
    }

    async fetchAuthenticationStatus() {
        try {
            const response = await fetch('/auth/authentication-status', {
                method: 'get',
                credentials: 'same-origin'
            });
            if (response.ok) {
                console.log('Authentication data received')
                return await response.json();
            } else {
                throw new Error('Network response was not ok.');
            }
        } catch (error) {
            console.error('Error fetching authentication status:', error);
        }
    }

    async login() {
        let loginForm = document.getElementById('login-form');
        let formData = new FormData(loginForm);
        return await fetch('/auth/login', {
            method: 'post',
            body: formData,
            credentials: 'same-origin'
        });
    }

    async logout() {
        try {
            const response = await fetch('/auth/logout', {
                method: 'GET',
                credentials: 'same-origin'
            });
            if (response.ok) {
                window.location.reload();
            } else {
                console.error("Error occurred when customer tried to logout.")
                window.location.href = '/';
            }
        } catch (error) {
            console.error(error);
        }
    }

}

export default AuthenticationService;
