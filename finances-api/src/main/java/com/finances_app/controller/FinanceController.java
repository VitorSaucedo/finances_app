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

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @PostMapping("/incomes")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        Income savedIncome = financeService.addIncome(income);
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense savedExpense = financeService.addExpense(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping("/statement")
    public ResponseEntity<Map<String, Object>> getMonthlyStatement(@RequestParam String period) {
        YearMonth yearMonth = YearMonth.parse(period); // Ex: "2025-09"

        List<Income> allIncomes = financeService.getAllIncomes();
        List<Expense> allExpenses = financeService.getAllExpenses();

        List<Income> periodIncomes = financeService.filterIncomesByPeriod(allIncomes, yearMonth);
        List<Expense> periodExpenses = financeService.filterExpensesByPeriod(allExpenses, yearMonth);

        double balance = financeService.calculateBalance(periodIncomes, periodExpenses);

        Map<String, Object> response = new HashMap<>();
        response.put("incomes", periodIncomes);
        response.put("expenses", periodExpenses);
        response.put("balance", balance);

        return ResponseEntity.ok(response);
    }
}