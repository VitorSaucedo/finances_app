const API_URL = '/api/transactions';
const params = new URLSearchParams(window.location.search);
const id = params.get('id');

document.addEventListener("DOMContentLoaded", async () => {
    if (id) {
        document.getElementById('formTitle').innerText = "Editar Transação";
        document.title = "Editar Transação";
        await loadTransaction(id);
    } else {
        // Define data de hoje como padrão para novos registros
        document.getElementById('date').valueAsDate = new Date();
    }
});

async function loadTransaction(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error("Transação não encontrada");

        const t = await response.json();

        document.getElementById('id').value = t.id;
        document.getElementById('description').value = t.description;

        // || '' para evitar que apareça "undefined" se estiver vazio
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

    const transaction = {
        id: document.getElementById('id').value || null,
        description: document.getElementById('description').value,

        category: document.getElementById('category').value,

        amount: document.getElementById('amount').value,
        date: document.getElementById('date').value,
        type: document.querySelector('input[name="type"]:checked').value
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(transaction)
        });

        if (response.ok) {
            window.location.href = 'index.html';
        } else {
            alert('Erro ao salvar.');
        }
    } catch (error) {
        console.error(error);
        alert('Erro de conexão.');
    }
});