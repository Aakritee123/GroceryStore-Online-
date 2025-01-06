 // Form validation logic
    const form = document.getElementById('registerForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent form submission

        let isValid = true;

        // Full Name Validation
        const fullName = document.getElementById('fullName');
        if (fullName.value.length < 3) {
            fullName.classList.add('is-invalid');
            isValid = false;
        } else {
            fullName.classList.remove('is-invalid');
        }

        // Username Validation
        const username = document.getElementById('username');
        if (username.value.length < 5) {
            username.classList.add('is-invalid');
            isValid = false;
        } else {
            username.classList.remove('is-invalid');
        }

        // Email Validation
        const email = document.getElementById('email');
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(email.value)) {
            email.classList.add('is-invalid');
            isValid = false;
        } else {
            email.classList.remove('is-invalid');
        }

        // Password Validation
        const password = document.getElementById('password');
        if (password.value.length < 6) {
            password.classList.add('is-invalid');
            isValid = false;
        } else {
            password.classList.remove('is-invalid');
        }

        // Confirm Password Validation
        const confirmPassword = document.getElementById('confirmPassword');
        if (confirmPassword.value !== password.value) {
            confirmPassword.classList.add('is-invalid');
            isValid = false;
        } else {
            confirmPassword.classList.remove('is-invalid');
        }

        // Submit if valid
        if (isValid) {
            form.submit();
        }
    });