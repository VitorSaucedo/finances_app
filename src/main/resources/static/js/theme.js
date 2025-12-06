// Verifica se já existe um tema salvo ou usa a preferência do sistema
const getPreferredTheme = () => {
    const storedTheme = localStorage.getItem('theme');
    if (storedTheme) {
        return storedTheme;
    }
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
};

// Aplica o tema no HTML
const setTheme = function (theme) {
    document.documentElement.setAttribute('data-bs-theme', theme);
    localStorage.setItem('theme', theme);

    // Muda o ícone do botão (se ele existir na página)
    const btn = document.getElementById('themeToggle');
    if (btn) {
        btn.innerText = theme === 'dark' ? '☀️' : '🌙';
        btn.className = theme === 'dark' ? 'btn btn-outline-light' : 'btn btn-outline-dark';
    }
};

// Evento de clique do botão
function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-bs-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    setTheme(newTheme);
}

// Inicializa assim que carrega
document.addEventListener('DOMContentLoaded', () => {
    setTheme(getPreferredTheme());
});