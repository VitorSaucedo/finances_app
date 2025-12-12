const API_URL = '/transactions';
const params = new URLSearchParams(window.location.search);
const id = params.get('id');

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

document.addEventListener("DOMContentLoaded", async () => {
    if (id) {
        document.getElementById('formTitle').innerText = "Editar Transação";
        document.title = "Editar Transação";
        await loadTransaction(id);
    } else {
        document.getElementById('date').valueAsDate = new Date();
    }
});

async function loadTransaction(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error("Transação não encontrada");

        const t = await response.json();

        // Preenche o formulario
        document.getElementById('id').value = t.id;
        document.getElementById('description').value = t.description;
        document.getElementById('category').value = t.category || '';
        document.getElementById('amount').value = t.amount;
        document.getElementById('date').value = t.date;

        if (t.type === 'INCOME') {
            document.getElementById('typeIncome').checked = true;
        } else {
            document.getElementById('typeExpense').checked = true;
        }
    } catch (error) {
        console.error(error);
        alert("Erro ao carregar dados.");
        window.location.href = "index.html";
    }
}

document.getElementById('transactionForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('id').value;

    const transactionData = {
        description: document.getElementById('description').value,
        category: document.getElementById('category').value,
        amount: document.getElementById('amount').value,
        date: document.getElementById('date').value,
        type: document.querySelector('input[name="type"]:checked').value
    };

    // Diferencia POST de PUT
    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                // ADICIONADO: Token CSRF
                'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')
            },
            body: JSON.stringify(transactionData)
        });
    } catch (error) {
        console.error(error);
        alert('Erro de conexão.');
    }
});