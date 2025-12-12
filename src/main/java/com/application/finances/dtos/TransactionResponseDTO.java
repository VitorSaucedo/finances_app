package com.application.finances.dtos;

import com.application.finances.entities.Transaction;
import com.application.finances.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDTO(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType type,
        String category
) {

    public TransactionResponseDTO(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getCategory()
        );
    }
}