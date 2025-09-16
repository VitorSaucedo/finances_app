package com.finances_app.services;

import com.finances_app.entities.Expense;
import com.finances_app.entities.Income;
import com.finances_app.repositories.ExpenseRepository;
import com.finances_app.repositories.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

// @Service marks this class as a Spring service, indicating it contains business logic.
@Service
public class FinanceService {

    // The repositories are interfaces that provide database operations for each entity.
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    // Constructor-based dependency injection to provide the repository instances.
    public FinanceService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    // --- Basic CRUD Operations ---

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

    public Income updateIncome(Long id, Income incomeDetails) {
        // Fetches the existing income or throws an exception if it's not found.
        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found for id: " + id));

        // Updates the fields of the existing entity with the new details.
        existingIncome.setDate(incomeDetails.getDate());
        existingIncome.setValue(incomeDetails.getValue());
        existingIncome.setDescription(incomeDetails.getDescription());
        existingIncome.setSource(incomeDetails.getSource());

        // Saves the updated entity back to the database.
        return incomeRepository.save(existingIncome);
    }

    public void deleteIncome(Long id) {
        // Checks for existence before attempting to delete to provide a clear error message.
        if (!incomeRepository.existsById(id)) {
            throw new EntityNotFoundException("Income not found for id: " + id);
        }
        incomeRepository.deleteById(id);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found for id: " + id));

        existingExpense.setDate(expenseDetails.getDate());
        existingExpense.setValue(expenseDetails.getValue());
        existingExpense.setDescription(expenseDetails.getDescription());
        existingExpense.setCategory(expenseDetails.getCategory());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense not found for id: " + id);
        }
        expenseRepository.deleteById(id);
    }

    // --- Business Logic Methods ---

    /**
     * Calculates the balance by summing up incomes and expenses.
     */
    public double calculateBalance(List<Income> incomes, List<Expense> expenses) {
        // Uses Java Streams to efficiently sum the values.
        double totalIncomes = incomes.stream().mapToDouble(Income::getValue).sum();
        // The expense value is already negative, so we can just sum it directly.
        double totalExpenses = expenses.stream().mapToDouble(Expense::getValue).sum();
        return totalIncomes + totalExpenses;
    }

    /**
     * Filters a list of incomes to return only those within a specific month and year.
     */
    public List<Income> filterIncomesByPeriod(List<Income> allIncomes, YearMonth period) {
        return allIncomes.stream()
                // The filter keeps only incomes where the YearMonth matches the specified period.
                .filter(income -> YearMonth.from(income.getDate()).equals(period))
                // Collects the filtered results into a new list.
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of expenses to return only those within a specific month and year.
     */
    public List<Expense> filterExpensesByPeriod(List<Expense> allExpenses, YearMonth period) {
        return allExpenses.stream()
                .filter(expense -> YearMonth.from(expense.getDate()).equals(period))
                .collect(Collectors.toList());
    }
}