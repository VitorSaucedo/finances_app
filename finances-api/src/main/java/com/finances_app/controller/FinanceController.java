package com.finances_app.controller;

import com.finances_app.entities.Expense;
import com.finances_app.entities.Income;
import com.finances_app.services.FinanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController marks this class as a REST controller, meaning its methods will return data (like JSON) directly.
@RestController
// @RequestMapping sets the base URL path for all endpoints in this class to "/api".
@RequestMapping("/api")
public class FinanceController {

    // The service layer that contains the business logic. It's marked as final for immutability.
    private final FinanceService financeService;

    // This is constructor-based dependency injection. Spring provides the FinanceService instance.
    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    // --- CREATE Endpoints ---

    // Handles HTTP POST requests to "/api/incomes" to create a new income record.
    @PostMapping("/incomes")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        Income savedIncome = financeService.addIncome(income);
        // Returns the created income object and an HTTP 201 CREATED status.
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }

    // Handles HTTP POST requests to "/api/expenses" to create a new expense record.
    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense savedExpense = financeService.addExpense(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    // --- READ Endpoint ---

    // Handles HTTP GET requests to "/api/statement" to fetch a monthly financial summary.
    @GetMapping("/statement")
    public ResponseEntity<Map<String, Object>> getMonthlyStatement(@RequestParam String period) {
        // @RequestParam extracts the 'period' parameter from the URL (e.g., ?period=2025-09).
        YearMonth yearMonth = YearMonth.parse(period);

        // Fetches all income and expense records.
        List<Income> allIncomes = financeService.getAllIncomes();
        List<Expense> allExpenses = financeService.getAllExpenses();

        // Filters the records to include only those from the specified period.
        List<Income> periodIncomes = financeService.filterIncomesByPeriod(allIncomes, yearMonth);
        List<Expense> periodExpenses = financeService.filterExpensesByPeriod(allExpenses, yearMonth);

        // Calculates the final balance for the month.
        double balance = financeService.calculateBalance(periodIncomes, periodExpenses);

        // Creates a Map to structure the JSON response.
        Map<String, Object> response = new HashMap<>();
        response.put("incomes", periodIncomes);
        response.put("expenses", periodExpenses);
        response.put("balance", balance);

        // Returns the response map with an HTTP 200 OK status.
        return ResponseEntity.ok(response);
    }

    // --- UPDATE Endpoints ---

    // Handles HTTP PUT requests to "/api/incomes/{id}" to update an existing income.
    @PutMapping("/incomes/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody Income incomeDetails) {
        // @PathVariable extracts the 'id' from the URL path. @RequestBody gets the updated data.
        Income updatedIncome = financeService.updateIncome(id, incomeDetails);
        return ResponseEntity.ok(updatedIncome);
    }

    // Handles HTTP PUT requests to "/api/expenses/{id}" to update an existing expense.
    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails) {
        Expense updatedExpense = financeService.updateExpense(id, expenseDetails);
        return ResponseEntity.ok(updatedExpense);
    }

    // --- DELETE Endpoints ---

    // Handles HTTP DELETE requests to "/api/incomes/{id}" to delete an income.
    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        financeService.deleteIncome(id);
        // Returns an HTTP 204 NO CONTENT status, indicating successful deletion with no response body.
        return ResponseEntity.noContent().build();
    }

    // Handles HTTP DELETE requests to "/api/expenses/{id}" to delete an expense.
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        financeService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}