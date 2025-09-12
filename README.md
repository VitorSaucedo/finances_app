# Finances Management System

A command-line application for personal finance tracking and management.

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 17+
- Basic command-line knowledge

### Installation & Running
1. Clone the repository:
```bash
git clone https://github.com/yourusername/finances_app.git
```

2. Compile the application:
```bash
javac -d out src/com/finances_app/**/*.java
```

3. Run the application:
```bash
java -cp out com.finances_app.application.Program
```

## 🏗️ Project Structure
```
src/
├── com/
│   └── finances_app/
│       ├── application/    # Main application entry point
│       ├── entities/       # Domain models (Expense, Income, Transaction)
│       ├── enums/          # Enumerations (TransactionType)
│       ├── repositories/   # Data access layer
│       ├── services/       # Business logic services
│       └── ui/             # Console user interface
```

## ✨ Key Features
- 💰 Track income and expenses
- 📊 Generate financial reports
- 🗂️ Categorize transactions
- 📈 View financial trends
- 🔄 Recurring transaction management

## 🧩 Core Components
| Component | Description |
|-----------|-------------|
| `Expense.java` | Represents an expense transaction |
| `Income.java` | Represents an income transaction |
| `Transaction.java` | Base class for all financial transactions |
| `FinanceService.java` | Core business logic for financial operations |
| `ConsoleUI.java` | Text-based user interface |

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.