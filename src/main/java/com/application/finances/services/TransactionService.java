package com.application.finances.services;

import com.application.finances.dtos.MonthlySummaryDTO;
import com.application.finances.entities.Transaction;
import com.application.finances.entities.User; // Importante
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
        // Verificar se a transação pertence ao usuário antes de salvar.
        return repository.save(transaction);
    }

    // --- Delete ---
    public void delete(Long id, User user) {
        Transaction transaction = findById(id, user);
        repository.delete(transaction);
    }

    // --- Read ---
    // Busca pelo ID e verifica usuario logado
    public Transaction findById(Long id, User user) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Se o dono da transacao não for o usuário passado, lanca erro
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denied: You cannot access this transaction");
        }

        return transaction;
    }

    // --- Resumo do Mes (Filtrado por Usuario) ---
    public MonthlySummaryDTO getMonthlySummary(int year, int month, User user) {
        // Calcula primeiro e ultimo dia do mês
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Busca transações do usuario
        List<Transaction> transactions = repository.findByDateBetweenAndUserOrderByDateDesc(startDate, endDate, user);

        // Busca totais do usuário
        BigDecimal income = repository.getSumByTypeAndDate(TransactionType.INCOME, startDate, endDate, user);
        if (income == null) income = BigDecimal.ZERO;

        BigDecimal expense = repository.getSumByTypeAndDate(TransactionType.EXPENSE, startDate, endDate, user);
        if (expense == null) expense = BigDecimal.ZERO;

        // Calcula saldo
        BigDecimal balance = income.subtract(expense);

        return new MonthlySummaryDTO(transactions, income, expense, balance);
    }
}