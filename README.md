# Finances App

Aplicação para gestão de finanças pessoais com backend em Spring Boot e frontend estático.

## Pré-requisitos
- Java 17+
- Maven (já incluído no projeto como `mvnw`)

## Executando a aplicação

### Backend (API)
```bash
cd finances-api
./mvnw spring-boot:run
```

### Frontend
Abra o arquivo `frontend/index.html` em qualquer navegador.

## Estrutura do Projeto
```
finances-app/
├── finances-api/       # Backend Spring Boot
│   ├── src/
│   │   ├── main/java/com/finances_app/
│   │   │   ├── controller/    # Controladores REST
│   │   │   ├── entities/      # Entidades JPA
│   │   │   ├── enums/         # Enumeradores
│   │   │   ├── repositories/  # Repositórios Spring Data
│   │   │   ├── services/      # Lógica de negócio
│   │   │   └── FinancesApiApplication.java # Classe main
│   │   └── resources/         # Configurações e recursos
│   └── pom.xml                # Dependências Maven
│
└── frontend/            # Interface web
    ├── app.js           # Lógica JavaScript
    ├── index.html       # Página principal
    └── style.css        # Estilos
```

## Tecnologias Utilizadas
- **Backend**: 
  - Spring Boot 3
  - Spring Data JPA
  - Maven

- **Frontend**:
  - HTML5
  - CSS3
  - JavaScript Vanilla