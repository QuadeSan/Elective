const usernameEl = document.querySelector('#name');
const lastnameEl = document.querySelector('#lastName');
const loginEl = document.querySelector('#login');
const emailEl = document.querySelector('#email');
const passwordEl = document.querySelector('#psw');
const confirmPasswordEl = document.querySelector('#psw-repeat');

const form = document.querySelector('#for-validation');


const checkUsername = () => {

    let valid = false;

    const min = 3,
        max = 15;

    const username = usernameEl.value.trim();

    if (!isRequired(username)) {
        showError(usernameEl, 'Name cannot be blank.');
    } else if (!isBetween(username.length, min, max)) {
        showError(usernameEl, `Name must be between ${min} and ${max} characters.`)
    } else {
        showSuccess(usernameEl);
        valid = true;
    }
    return valid;
};

const checkLastName = () => {

    let valid = false;

    const min = 3,
        max = 15;

    const lastname = lastnameEl.value.trim();

    if (!isRequired(lastname)) {
        showError(lastnameEl, 'Last name cannot be blank.');
    } else if (!isBetween(lastname.length, min, max)) {
        showError(lastnameEl, `Last name must be between ${min} and ${max} characters.`)
    } else {
        showSuccess(lastnameEl);
        valid = true;
    }
    return valid;
};

const checkLogin = () => {

    let valid = false;

    const min = 3,
        max = 15;

    const login = loginEl.value.trim();

    if (!isRequired(login)) {
        showError(loginEl, 'Login cannot be blank.');
    } else if (!isBetween(login.length, min, max)) {
        showError(loginEl, `Login must be between ${min} and ${max} characters.`)
    } else {
        showSuccess(loginEl);
        valid = true;
    }
    return valid;
};


const checkEmail = () => {
    let valid = false;
    const email = emailEl.value.trim();
    if (!isRequired(email)) {
        showError(emailEl, 'Email cannot be blank.');
    } else if (!isEmailValid(email)) {
        showError(emailEl, 'Email is not valid.')
    } else {
        showSuccess(emailEl);
        valid = true;
    }
    return valid;
};

const checkPassword = () => {
    let valid = false;

    const password = passwordEl.value.trim();

    if (!isRequired(password)) {
        showError(passwordEl, 'Password cannot be blank.');
    } else {
        showSuccess(passwordEl);
        valid = true;
    }

    return valid;
};

const checkConfirmPassword = () => {
    let valid = false;

    const confirmPassword = confirmPasswordEl.value.trim();
    const password = passwordEl.value.trim();

    if (!isRequired(confirmPassword)) {
        showError(confirmPasswordEl, 'Please enter the password again');
    } else if (password !== confirmPassword) {
        showError(confirmPasswordEl, 'The password does not match');
    } else {
        showSuccess(confirmPasswordEl);
        valid = true;
    }

    return valid;
};

const isEmailValid = (email) => {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};


const isRequired = value => value === '' ? false : true;
const isBetween = (length, min, max) => length < min || length > max ? false : true;


const showError = (input, message) => {
    const formField = input.parentElement;

    formField.classList.remove('success');
    formField.classList.add('error');

    const error = formField.querySelector('small');
    error.textContent = message;
};

const showSuccess = (input) => {
    const formField = input.parentElement;

    formField.classList.remove('error');
    formField.classList.add('success');

    const error = formField.querySelector('small');
    error.textContent = '';
}


form.addEventListener('submit', function (e) {
    e.preventDefault();

    let isUsernameValid = checkUsername(),
        isLastnameValid = checkLastName(),
        isLoginValid = checkLogin(),
        isEmailValid = checkEmail(),
        isPasswordValid = checkPassword(),
        isConfirmPasswordValid = checkConfirmPassword();

    let isFormValid = isUsernameValid &&
        isLastnameValid &&
        isLoginValid &&
        isEmailValid &&
        isPasswordValid &&
        isConfirmPasswordValid;

    if (isFormValid) {
        document.getElementById("for-validation").submit();
    }
});


const debounce = (fn, delay = 500) => {
    let timeoutId;
    return (...args) => {
        if (timeoutId) {
            clearTimeout(timeoutId);
        }
        timeoutId = setTimeout(() => {
            fn.apply(null, args)
        }, delay);
    };
};

form.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'name':
            checkUsername();
            break;
        case 'lastName':
            checkLastName();
            break;
        case 'login':
            checkLogin();
            break;
        case 'email':
            checkEmail();
            break;
        case 'psw':
            checkPassword();
            break;
        case 'psw-repeat':
            checkConfirmPassword();
            break;
    }
}));