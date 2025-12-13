document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);

    if (params.has('error')) {
        const errorAlert = document.getElementById('errorAlert');
        if (errorAlert) errorAlert.classList.remove('d-none');
    }

    if (params.has('registered')) {
        const successAlert = document.getElementById('successAlert');
        if (successAlert) successAlert.classList.remove('d-none');
    }

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    const token = getCookie('XSRF-TOKEN');

    if (token) {
        const form = document.querySelector('form');

        if (form) {
            const csrfInput = document.createElement('input');
            csrfInput.type = 'hidden';
            csrfInput.name = '_csrf';

            csrfInput.value = token;
            form.appendChild(csrfInput);
            console.log("Token CSRF injetado no formulário como '_csrf'.");
        }
    } else {
        console.warn("Cookie XSRF-TOKEN não encontrado. O login pode falhar.");
    }
});
