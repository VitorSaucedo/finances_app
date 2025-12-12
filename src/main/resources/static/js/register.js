function getCookie(name) {
    if (!document.cookie) {
        return null;
    }
    const xsrfCookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith(name + '='));

    if (xsrfCookies.length === 0) {
        return null;
    }
    return decodeURIComponent(xsrfCookies[0].split('=')[1]);
}

document.getElementById('registerForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Impede o recarregamento da página

    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const btnSubmit = document.getElementById('btnSubmit');
    const feedbackAlert = document.getElementById('feedbackAlert');

    // 1. Prepara os dados
    const registerData = {
        username: usernameInput.value,
        password: passwordInput.value
    };

    // 2. Feedback visual (Carregando...)
    btnSubmit.disabled = true;
    btnSubmit.innerHTML = 'Criando conta...';
    feedbackAlert.classList.add('d-none');

    try {
        const response = await fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // ADICIONADO: Token CSRF
                'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')
            },
            body: JSON.stringify(registerData)
        });

        // 4. Verifica o sucesso
        if (response.ok) {
            // Sucesso: Redireciona para o login com mensagem
            window.location.href = '/login.html?registered=true';
        } else {
            // Erro: Tenta ler a mensagem do backend ou usa uma genérica
            let errorMessage = "Erro ao criar conta.";
            try {
                const errorText = await response.text();
                if (errorText) errorMessage = errorText;
            } catch (e) {}

            showError(errorMessage);
            resetButton();
        }

    } catch (error) {
        showError("Erro de conexão com o servidor.");
        resetButton();
        console.error(error);
    }

    // Funções auxiliares
    function showError(msg) {
        feedbackAlert.textContent = msg;
        feedbackAlert.className = 'alert alert-danger'; // Vermelho
        feedbackAlert.classList.remove('d-none');
    }

    function resetButton() {
        btnSubmit.disabled = false;
        btnSubmit.innerHTML = 'Criar Conta';
    }
});