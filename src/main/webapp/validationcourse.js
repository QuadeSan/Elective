const titleEl = document.querySelector('#title');
const topicEl = document.querySelector('#topic');

const form = document.querySelector('#for-validation');

const checkTitle = () => {

    let valid = false;

    const min = 3,
        max = 15;

    const title = titleEl.value.trim();

    if (!isRequired(title)) {
        showError(titleEl, 'Last name cannot be blank.');
    } else if (!isBetween(title.length, min, max)) {
        showError(titleEl, `Title must be between ${min} and ${max} characters.`)
    } else {
        showSuccess(titleEl);
        valid = true;
    }
    return valid;
};

const checkTopic = () => {

    let valid = false;

    const min = 3,
        max = 15;

    const topic = topicEl.value.trim();

    if (!isRequired(topic)) {
        showError(topicEl, 'Login cannot be blank.');
    } else if (!isBetween(topic.length, min, max)) {
        showError(topicEl, `Topic must be between ${min} and ${max} characters.`)
    } else {
        showSuccess(topicEl);
        valid = true;
    }
    return valid;
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

    let isTitleValid = checkTitle(),
        isTopicValid = checkTopic();

    let isFormValid = isTitleValid &&
        isTopicValid;

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
        case 'title':
            checkTitle();
            break;
        case 'topic':
            checkTopic();
            break;
    }
}));