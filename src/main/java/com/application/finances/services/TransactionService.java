package com.application.finances.services;

import com.application.finances.dtos.MonthlySummaryDTO;
import com.application.finances.entities.Transaction;
import com.application.finances.entities.User;
import com.application.finances.enums.TransactionType;
import com.application.finances.exceptions.ForbiddenException; // Importado
import com.application.finances.exceptions.ResourceNotFoundException; // Importado
import com.application.finances.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    // --- Create / Update ---
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    // --- Delete ---
    public void delete(Long id, User user) {
        Transaction transaction = findById(id, user);
        repository.delete(transaction);
    }

    // --- Read ---
    public Transaction findById(Long id, User user) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

        // Validação de segurança: Usuário só pode acessar seus próprios dados
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Access Denied: You cannot access this transaction");
        }

        return transaction;
    }

    // --- Resumo do Mes ---
    public MonthlySummaryDTO getMonthlySummary(int year, int month, User user) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Transaction> transactions = repository.findByDateBetweenAndUserOrderByDateDesc(startDate, endDate, user);

        BigDecimal income = repository.getSumByTypeAndDate(TransactionType.INCOME, startDate, endDate, user);
        if (income == null) income = BigDecimal.ZERO;

        BigDecimal expense = repository.getSumByTypeAndDate(TransactionType.EXPENSE, startDate, endDate, user);
        if (expense == null) expense = BigDecimal.ZERO;

        BigDecimal balance = income.subtract(expense);

        return new MonthlySummaryDTO(transactions, income, expense, balance);
    }
}