package com.application.finances.dtos;

import com.application.finances.entities.Transaction;
import java.math.BigDecimal;
import java.util.List;

public record MonthlySummaryDTO(
        List<Transaction> transactions,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {}
