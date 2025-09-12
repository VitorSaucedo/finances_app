package com.finances_app.application;

import com.finances_app.entities.Expense;
import com.finances_app.entities.Income;
import com.finances_app.repositories.ExpenseRepository;
import com.finances_app.repositories.IncomeRepository;
import com.finances_app.services.FinanceService;
import com.finances_app.ui.ConsoleUI;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Program {

    private final IncomeRepository incomeRepo = new IncomeRepository();
    private final ExpenseRepository expenseRepo = new ExpenseRepository();
    private final FinanceService financeService = new FinanceService();
    private final ConsoleUI ui = new ConsoleUI();

    private List<Income> incomes;
    private List<Expense> expenses;

    public static void main(String[] args) {
        new Program().run();
    }

    public void run() {
        incomes = incomeRepo.loadIncomes();
        expenses = expenseRepo.loadExpenses();
        ui.showMessage("--- Personal Finance Manager ---");

        boolean running = true;
        while (running) {
            int choice = ui.displayMainMenuAndGetChoice();
            switch (choice) {
                case 1:
                    handleAddIncome();
                    break;
                case 2:
                    handleAddExpense();
                    break;
                case 3:
                    handleViewMonthlyStatement();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    ui.showErrorMessage("Invalid option. Please try again.");
                    break;
            }
        }
        ui.showMessage("Goodbye!");
    }

    private void handleAddIncome() {
        Income newIncome = ui.getNewIncomeDetails();
        if (newIncome != null) {
            incomes.add(newIncome);
            incomeRepo.saveIncomes(incomes);
            ui.showMessage("Income added successfully!");
        }
    }

    private void handleAddExpense() {
        Expense newExpense = ui.getNewExpenseDetails();
        if (newExpense != null) {
            expenses.add(newExpense);
            expenseRepo.saveExpenses(expenses);
            ui.showMessage("Expense added successfully!");
        }
    }

    private void handleViewMonthlyStatement() {
        YearMonth period = ui.getYearMonthForStatement();
        if (period != null) {
            List<Income> monthlyIncomes = financeService.filterIncomesByPeriod(incomes, period);
            List<Expense> monthlyExpenses = financeService.filterExpensesByPeriod(expenses, period);
            double balance = financeService.calculateBalance(monthlyIncomes, monthlyExpenses);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
            String formattedPeriod = period.format(formatter);

            ui.displayMonthlyStatement(formattedPeriod, monthlyIncomes, monthlyExpenses, balance);
        }
    }
}
