const API_URL = '/api/transactions';

// Executa assim que a página carrega
document.addEventListener("DOMContentLoaded", () => {
    const today = new Date();
    // Define o mês e ano atuais nos inputs
    document.getElementById('month').value = today.getMonth() + 1;
    document.getElementById('year').value = today.getFullYear();

    // Carrega os dados iniciais
    loadData();
});

async function loadData() {
    const month = document.getElementById('month').value;
    const year = document.getElementById('year').value;

    try {
        const response = await fetch(`${API_URL}?year=${year}&month=${month}`);
        if (!response.ok) throw new Error('Falha na requisição');

        const data = await response.json();
        renderDashboard(data);
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao carregar dados. Verifique o console.');
    }
}

function renderDashboard(data) {
    // Atualiza os Cards de Totais
    document.getElementById('totalIncome').innerText = formatCurrency(data.totalIncome);
    document.getElementById('totalExpense').innerText = formatCurrency(data.totalExpense);

    const balanceElem = document.getElementById('balance');
    balanceElem.innerText = formatCurrency(data.balance);

    // Remove classes antigas e adiciona a nova cor baseada no saldo
    balanceElem.classList.remove('positive', 'negative');
    balanceElem.classList.add(data.balance >= 0 ? 'positive' : 'negative');

    // Atualiza a Tabela
    const tbody = document.getElementById('tableBody');
    tbody.innerHTML = ''; // Limpa a tabela

    if (data.transactions.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">Nenhuma transação neste mês.</td></tr>';
        return;
    }

    data.transactions.forEach(t => {
        const row = `
            <tr>
                <td>${formatDate(t.date)}</td>
                <td>${t.description}</td>

                <td>
                    ${t.category
                        ? `<span class="category-tag">${t.category}</span>`
                        : '<span class="text-muted">-</span>'
                    }
                </td>

                <td>
                    <span class="badge ${t.type === 'INCOME' ? 'bg-success' : 'bg-danger'}">
                        ${t.type === 'INCOME' ? 'Receita' : 'Despesa'}
                    </span>
                </td>
                <td class="${t.type === 'INCOME' ? 'positive' : 'negative'}">
                    ${formatCurrency(t.amount)}
                </td>
                <td>
                    <a href="form.html?id=${t.id}" class="btn btn-sm btn-outline-primary">Editar</a>
                    <button onclick="deleteTransaction(${t.id})" class="btn btn-sm btn-outline-danger">Excluir</button>
                </td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

// Função global para ser chamada pelo botão onclick
window.deleteTransaction = async function(id) {
    if(confirm('Tem certeza que deseja excluir esta transação?')) {
        try {
            await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
            loadData(); // Recarrega a tela para atualizar saldo e tabela
        } catch (error) {
            alert('Erro ao excluir.');
        }
    }
}

// --- Funções Auxiliares de Formatação ---

function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}

function formatDate(dateStr) {
    // dateStr vem como YYYY-MM-DD
    const [year, month, day] = dateStr.split('-');
    return `${day}/${month}/${year}`;
}