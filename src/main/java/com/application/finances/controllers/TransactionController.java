package com.application.finances.controllers;

import com.application.finances.config.CustomUserDetails;
import com.application.finances.dtos.CreateTransactionDTO;
import com.application.finances.dtos.MonthlySummaryDTO;
import com.application.finances.dtos.TransactionResponseDTO;
import com.application.finances.entities.Transaction;
import com.application.finances.entities.User;
import com.application.finances.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    private User getLoggedUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping
    public ResponseEntity<MonthlySummaryDTO> getSummary(@RequestParam(required = false) Integer year,
                                                        @RequestParam(required = false) Integer month,
                                                        Authentication authentication) {
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();

        User user = getLoggedUser(authentication);
        return ResponseEntity.ok(service.getMonthlySummary(year, month, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getById(@PathVariable Long id, Authentication authentication) {
        User user = getLoggedUser(authentication);

        Transaction transaction = service.findById(id, user);

        // Conversão Entidade -> DTO
        return ResponseEntity.ok(new TransactionResponseDTO(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        User user = getLoggedUser(authentication);
        service.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateTransactionDTO data, Authentication authentication) {
        User user = getLoggedUser(authentication);

        Transaction newTransaction = new Transaction();
        newTransaction.setDescription(data.description());
        newTransaction.setAmount(data.amount());
        newTransaction.setDate(data.date());
        newTransaction.setType(data.type());
        newTransaction.setCategory(data.category());
        newTransaction.setUser(user);

        service.save(newTransaction);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id,
                                                         @RequestBody @Valid CreateTransactionDTO data,
                                                         Authentication authentication) {
        User user = getLoggedUser(authentication);

        Transaction transaction = service.findById(id, user);

        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setDate(data.date());
        transaction.setType(data.type());
        transaction.setCategory(data.category());

        service.save(transaction);

        return ResponseEntity.ok(new TransactionResponseDTO(transaction));
    }
}