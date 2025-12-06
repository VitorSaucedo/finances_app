package com.application.finances.services;

import com.application.finances.dtos.MonthlySummaryDTO;
import com.application.finances.entities.Transaction;
import com.application.finances.enums.TransactionType;
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
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // --- Read (Para edição) ---
    public Transaction findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    // --- Resumo do Mês ---
    public MonthlySummaryDTO getMonthlySummary(int year, int month) {
        // Calcula primeiro e último dia do mês
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Busca a lista de transações
        List<Transaction> transactions = repository.findByDateBetweenOrderByDateDesc(startDate, endDate);

        // Busca os totais (Se retornar null, vira Zero)
        BigDecimal income = repository.getSumByTypeAndDate(TransactionType.INCOME, startDate, endDate);
        if (income == null) income = BigDecimal.ZERO;

        BigDecimal expense = repository.getSumByTypeAndDate(TransactionType.EXPENSE, startDate, endDate);
        if (expense == null) expense = BigDecimal.ZERO;

        // Calcula o saldo
        BigDecimal balance = income.subtract(expense);

        // Empacota tudo e devolve
        return new MonthlySummaryDTO(transactions, income, expense, balance);
    }
}
