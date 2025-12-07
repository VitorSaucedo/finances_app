package com.application.finances.dtos;

import com.application.finances.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionDTO(
        @NotBlank String description,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate date,
        @NotNull TransactionType type,
        String category
) {}