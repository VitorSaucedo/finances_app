## 📋 Descrição do Projeto

O Finances App é uma aplicação web completa para gerenciamento financeiro pessoal. Permite que os usuários se registrem, façam login e registrem suas transações financeiras (receitas e despesas), organizadas por categorias e datas. A aplicação oferece um resumo mensal das finanças, mostrando receitas totais, despesas e saldo.

Funcionalidades principais:
- Registro e autenticação de usuários
- CRUD de transações financeiras
- Categorização de transações
- Resumo financeiro mensal
- Interface responsiva com suporte a temas claro/escuro

## 🏗️ Arquitetura Técnica

### Backend
- **Framework**: Spring Boot 4.0.0
- **Linguagem**: Java 21
- **Build Tool**: Maven
- **Arquitetura**: Monolítica com padrão MVC (Model-View-Controller)
- **Segurança**: Spring Security com autenticação baseada em formulário
- **Persistência**: Spring Data JPA com PostgreSQL
- **Validação**: Bean Validation (Jakarta Validation)
- **Documentação**: Código auto-documentado com JavaDoc

### Frontend
- **Páginas Estáticas**: HTML5, CSS3, JavaScript ES6
- **Framework CSS**: Bootstrap 5.3
- **Temas**: Suporte a modo claro/escuro
- **Integração**: Fetch API para comunicação com o backend

### Banco de Dados
- **SGBD**: PostgreSQL
- **Tipo**: Relacional
- **Modelo**:
- Usuários (tb_users)
- Transações (tb_transactions)
- **Relacionamento**: Um usuário possui muitas transações (1:N)

### Estrutura de Pacotes
```
com.application.finances
├── config          # Configurações de segurança e inicialização
├── controllers      # Controladores REST e Web
├── dtos             # Objetos de Transferência de Dados
├── entities         # Entidades JPA
├── enums            # Enumerações
├── repositories     # Repositórios Spring Data JPA
├── services         # Lógica de negócio
└── Application.java # Classe principal
```

## 🚀 Instruções de Instalação e Configuração

### Pré-requisitos
- Java 21 ou superior
- Maven 3.8+
- PostgreSQL 13+
- IDE de sua preferência (IntelliJ IDEA, VS Code, Eclipse)

### Configuração do Ambiente de Desenvolvimento

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd finances-app
```

2. **Configuração do Banco de Dados**
   - Crie um banco de dados PostgreSQL:
   ```sql
   CREATE DATABASE finances_app;
   ```

3. **Configuração das Variáveis de Ambiente**
   - Configure as variáveis de ambiente na sua IDE ou no seu Sistema Operacional.

4. **Instalação das Dependências**
```bash
mvn clean install
```

5. **Execução da Aplicação**
```bash
mvn spring-boot:run
```

6. **Acesso à Aplicação**
   - Acesse `http://localhost:8080` no seu navegador
   - Página inicial: Tela de login
   - Registro de novo usuário: `/register.html`

## 📡 Documentação das APIs

### Autenticação

#### Registrar Usuário
- **Endpoint**: `POST /auth/register`
- **Descrição**: Registra um novo usuário no sistema
- **Corpo da Requisição**:
```json
{
  "username": "usuario_exemplo",
  "password": "senha_segura"
}
```
- **Respostas**:
  - `200 OK`: Usuário registrado com sucesso
  - `400 Bad Request`: Usuário já existe

### Transações

#### Criar Transação
- **Endpoint**: `POST /transactions`
- **Descrição**: Cria uma nova transação para o usuário autenticado
- **Corpo da Requisição**:
```json
{
  "description": "Salário mensal",
  "amount": 5000.00,
  "date": "2025-12-01",
  "type": "INCOME",
  "category": "Salário"
}
```
- **Respostas**:
  - `201 Created`: Transação criada com sucesso
  - `400 Bad Request`: Dados inválidos

#### Listar Transações (Resumo Mensal)
- **Endpoint**: `GET /transactions`
- **Descrição**: Retorna o resumo financeiro do mês/ano especificado
- **Parâmetros de Query**:
  - `year`: Ano (opcional, padrão: ano atual)
  - `month`: Mês (opcional, padrão: mês atual)
- **Exemplo de Requisição**: `GET /transactions?year=2025&month=12`
- **Exemplo de Resposta**:
```json
{
  "transactions": [
    {
      "id": 1,
      "description": "Salário mensal",
      "amount": 5000.00,
      "date": "2025-12-01",
      "type": "INCOME",
      "category": "Salário"
    }
  ],
  "totalIncome": 5000.00,
  "totalExpense": 2000.00,
  "balance": 3000.00
}
```

#### Obter Transação por ID
- **Endpoint**: `GET /transactions/{id}`
- **Descrição**: Retorna os detalhes de uma transação específica
- **Respostas**:
  - `200 OK`: Detalhes da transação
  - `404 Not Found`: Transação não encontrada

#### Atualizar Transação
- **Endpoint**: `PUT /transactions/{id}`
- **Descrição**: Atualiza os dados de uma transação existente
- **Corpo da Requisição**: Mesmo formato de criação
- **Respostas**:
  - `200 OK`: Transação atualizada
  - `400 Bad Request`: Dados inválidos

#### Deletar Transação
- **Endpoint**: `DELETE /transactions/{id}`
- **Descrição**: Remove uma transação do sistema
- **Respostas**:
  - `204 No Content`: Transação removida com sucesso
  - `404 Not Found`: Transação não encontrada

## 📦 Dependências Principais

- `spring-boot-starter-webmvc`: Para criação de APIs REST e aplicações web
- `spring-boot-starter-data-jpa`: Para acesso a dados com JPA/Hibernate
- `spring-boot-starter-security`: Para segurança e autenticação
- `postgresql`: Driver JDBC para PostgreSQL
- `lombok`: Para reduzir boilerplate code
- `spring-boot-starter-validation`: Para validação de dados

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 🙏 Créditos

Desenvolvido por Vitor Saucedo como parte de um projeto educacional para demonstrar práticas de desenvolvimento full-stack com Spring Boot e Java.