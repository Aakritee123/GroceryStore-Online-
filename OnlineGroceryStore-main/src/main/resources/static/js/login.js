//// Form Validation
//    function validateLoginForm() {
//        let isValid = true;
//
//        // Validate Email
//        const emailInput = document.getElementById('email');
//        const emailError = emailInput.nextElementSibling;
//        if (!emailInput.value || !validateEmailFormat(emailInput.value)) {
//            emailInput.classList.add('is-invalid');
//            emailError.classList.remove('d-none');
//            isValid = false;
//        } else {
//            emailInput.classList.remove('is-invalid');
//            emailError.classList.add('d-none');
//        }
//
//        // Validate Password
//        const passwordInput = document.getElementById('password');
//        const passwordError = passwordInput.nextElementSibling;
//        if (!passwordInput.value || passwordInput.value.length < 6) {
//            passwordInput.classList.add('is-invalid');
//            passwordError.classList.remove('d-none');
//            isValid = false;
//        } else {
//            passwordInput.classList.remove('is-invalid');
//            passwordError.classList.add('d-none');
//        }
//
//        return isValid;
//    }
//
//    // Email format validation
//    function validateEmailFormat(email) {
//        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//        return emailRegex.test(email);
//    }
//
//    // Validate Email input (real-time)
//    function validateEmail() {
//        const emailInput = document.getElementById('email');
//        const emailError = emailInput.nextElementSibling;
//        if (!emailInput.value || !validateEmailFormat(emailInput.value)) {
//            emailInput.classList.add('is-invalid');
//            emailError.classList.remove('d-none');
//        } else {
//            emailInput.classList.remove('is-invalid');
//            emailError.classList.add('d-none');
//        }
//    }
//
//    // Validate Password input (real-time)
//    function validatePassword() {
//        const passwordInput = document.getElementById('password');
//        const passwordError = passwordInput.nextElementSibling;
//        if (!passwordInput.value || passwordInput.value.length < 6) {
//            passwordInput.classList.add('is-invalid');
//            passwordError.classList.remove('d-none');
//        } else {
//            passwordInput.classList.remove('is-invalid');
//            passwordError.classList.add('d-none');
//        }
//    }


function validateLoginForm() {
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    let isValid = true;

    // Validate email
    if (!validateEmail()) {
        email.classList.add('is-invalid');
        isValid = false;
    } else {
        email.classList.remove('is-invalid');
    }

    // Validate password
    if (!validatePassword()) {
        password.classList.add('is-invalid');
        isValid = false;
    } else {
        password.classList.remove('is-invalid');
    }

    return isValid;
}

function validateEmail() {
    const email = document.getElementById('email').value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePassword() {
    const password = document.getElementById('password').value.trim();
    return password.length >= 6;
}
