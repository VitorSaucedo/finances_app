package com.finances_app.services;

import com.finances_app.entities.Expense;
import com.finances_app.entities.Income;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class FinanceService {

    public double calculateBalance(List<Income> incomes, List<Expense> expenses) {
        double totalIncomes = incomes.stream().mapToDouble(Income::getValue).sum();
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