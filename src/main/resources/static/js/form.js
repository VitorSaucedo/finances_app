const API_URL = '/transactions';
const params = new URLSearchParams(window.location.search);
const id = params.get('id');

/**
 * Função para pegar cookies de forma segura
 * Verifica se o cookie existe antes de tentar dividir a string
 */
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

/**
 * Carrega os dados ao abrir a página
 */
document.addEventListener("DOMContentLoaded", async () => {
    if (id) {
        // Modo Edição
        document.getElementById('formTitle').innerText = "Editar Transação";
        document.title = "Editar Transação";
        await loadTransaction(id);
    } else {
        // Modo Criação: preenche a data com o dia atual
        document.getElementById('date').valueAsDate = new Date();
    }
});

/**
 * Busca os dados da transação para preencher o formulário
 */
async function loadTransaction(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error("Transação não encontrada");

        const t = await response.json();

        document.getElementById('id').value = t.id;
        document.getElementById('description').value = t.description;
        document.getElementById('category').value = t.category || '';
        document.getElementById('amount').value = t.amount;
        document.getElementById('date').value = t.date;

        // Marca o radio button correto
        if (t.type === 'INCOME') {
            document.getElementById('typeIncome').checked = true;
        } else {
            document.getElementById('typeExpense').checked = true;
        }
    } catch (error) {
        console.error(error);
        alert("Erro ao carregar dados. Redirecionando...");
        window.location.href = "index.html";
    }
}

/**
 * Envia o formulário (Create ou Update)
 */
document.getElementById('transactionForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    // UX: Desabilita o botão e muda o texto para evitar duplo clique
    const btnSubmit = document.querySelector('button[type="submit"]');
    const textoOriginal = btnSubmit.innerText;
    btnSubmit.disabled = true;
    btnSubmit.innerText = 'Salvando...';

    const id = document.getElementById('id').value;

    const transactionData = {
        description: document.getElementById('description').value,
        category: document.getElementById('category').value,
        amount: document.getElementById('amount').value,
        date: document.getElementById('date').value,
        type: document.querySelector('input[name="type"]:checked').value
    };

    // Determina se é PUT (atualizar) ou POST (criar)
    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')
            },
            body: JSON.stringify(transactionData)
        });

        if (response.ok) {
            // Sucesso: volta para a lista
            window.location.href = '/index.html';
        } else {
            // Erro vindo do backend
            const erroTexto = await response.text();
            alert('Erro ao salvar: ' + (erroTexto || response.statusText));

            // Reativa o botão em caso de erro
            btnSubmit.disabled = false;
            btnSubmit.innerText = textoOriginal;
        }
    } catch (error) {
        console.error(error);
        alert('Erro de conexão com o servidor.');

        // Reativa o botão em caso de erro
        btnSubmit.disabled = false;
        btnSubmit.innerText = textoOriginal;
    }
});