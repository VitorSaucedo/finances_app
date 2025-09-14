const API_BASE_URL = 'http://localhost:8080/api';

const formatCurrency = (value) => {
    return `R$ ${Math.abs(value).toFixed(2)}`;
};

const formatDateToMonthYear = (dateString) => {
    const [year, month] = dateString.split('-');
    return `${month}/${year}`;
};

function populateDateSelectors() {
    const monthSelect = document.getElementById('month-select');
    const yearSelect = document.getElementById('year-select');
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1;

    const months = [
        { name: 'Janeiro', value: '01' }, { name: 'Fevereiro', value: '02' },
        { name: 'Março', value: '03' }, { name: 'Abril', value: '04' },
        { name: 'Maio', value: '05' }, { name: 'Junho', value: '06' },
        { name: 'Julho', value: '07' }, { name: 'Agosto', value: '08' },
        { name: 'Setembro', value: '09' }, { name: 'Outubro', value: '10' },
        { name: 'Novembro', value: '11' }, { name: 'Dezembro', value: '12' }
    ];

    months.forEach(month => {
        const option = document.createElement('option');
        option.value = month.value;
        option.textContent = month.name;
        monthSelect.appendChild(option);
    });

    for (let i = currentYear - 5; i <= currentYear + 5; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        yearSelect.appendChild(option);
    }

    monthSelect.value = String(currentMonth).padStart(2, '0');
    yearSelect.value = currentYear;
}


async function fetchStatement() {
    const month = document.getElementById('month-select').value;
    const year = document.getElementById('year-select').value;
    const period = `${year}-${month}`;

    if (!period) {
        alert('Por favor, selecione um período.');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/statement?period=${period}`);
        if (!response.ok) {
            throw new Error('Erro ao buscar dados da API.');
        }
        const data = await response.json();

        updateUI(data);

    } catch (error) {
        console.error('Falha na requisição:', error);
        alert('Não foi possível carregar o extrato.');
    }
}

function updateUI(data) {
    const incomesTbody = document.querySelector('#incomes-table tbody');
    const expensesTbody = document.querySelector('#expenses-table tbody');

    incomesTbody.innerHTML = '';
    expensesTbody.innerHTML = '';

    data.incomes.forEach(income => {
        incomesTbody.innerHTML += `
            <tr>
                <td>${formatDateToMonthYear(income.date)}</td>
                <td>${income.description}</td>
                <td>${income.source}</td>
                <td>${formatCurrency(income.value)}</td>
            </tr>
        `;
    });

    data.expenses.forEach(expense => {
        expensesTbody.innerHTML += `
            <tr>
                <td>${formatDateToMonthYear(expense.date)}</td>
                <td>${expense.description}</td>
                <td>${expense.category}</td>
                <td>${formatCurrency(expense.value)}</td>
            </tr>
        `;
    });

    document.getElementById('balance').textContent = formatCurrency(data.balance);
}

document.getElementById('transaction-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const type = document.getElementById('type').value;
    const year = document.getElementById('year-select').value;
    const month = document.getElementById('month-select').value;
    const date = `${year}-${month}-01`;

    const transactionData = {
        date: date,
        description: document.getElementById('description').value,
        value: parseFloat(document.getElementById('value').value),
    };

    let url = '';
    if (type === 'income') {
        transactionData.source = document.getElementById('category-source').value;
        url = `${API_BASE_URL}/incomes`;
    } else {
        transactionData.category = document.getElementById('category-source').value;
        url = `${API_BASE_URL}/expenses`;
    }

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(transactionData)
        });

        if (!response.ok) throw new Error('Erro ao salvar transação.');

        alert('Transação adicionada com sucesso!');
        document.getElementById('transaction-form').reset();
        fetchStatement();
    } catch (error) {
        console.error('Falha ao adicionar transação:', error);
        alert('Não foi possível adicionar a transação.');
    }
});

window.onload = () => {
    populateDateSelectors();
    fetchStatement();
}