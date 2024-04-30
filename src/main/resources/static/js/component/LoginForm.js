class LoginForm {
    constructor() {
        console.log('Login form was created')
    }

    toggleLoginForm() {
        let loginPopup = document.getElementById('login-popup');
        loginPopup.style.display = loginPopup.style.display === "block" ? "none" : "block";
    }

    redirectToRegistrationPage() {
        window.location.href = "/auth/registration";
    }
}

export default LoginForm;
