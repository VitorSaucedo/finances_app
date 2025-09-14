package com.finances_app.services;

import com.finances_app.entities.Expense;
import com.finances_app.entities.Income;
import com.finances_app.repositories.ExpenseRepository;
import com.finances_app.repositories.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public FinanceService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public double calculateBalance(List<Income> incomes, List<Expense> expenses) {
        double totalIncomes = incomes.stream().mapToDouble(Income::getValue).sum();
        // O valor da despesa já é negativo, então somamos diretamente.
        double totalExpenses = expenses.stream().mapToDouble(Expense::getValue).sum();
        return totalIncomes + totalExpenses;
    }

    public List<Income> filterIncomesByPeriod(List<Income> allIncomes, YearMonth period) {
        return allIncomes.stream()
                .filter(income -> YearMonth.from(income.getDate()).equals(period))
                .collect(Collectors.toList());
    }

    public List<Expense> filterExpensesByPeriod(List<Expense> allExpenses, YearMonth period) {
        return allExpenses.stream()
                .filter(expense -> YearMonth.from(expense.getDate()).equals(period))
                .collect(Collectors.toList());
    }
}