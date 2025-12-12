package com.application.finances.controllers;

import com.application.finances.dtos.CreateTransactionDTO;
import com.application.finances.dtos.MonthlySummaryDTO;
import com.application.finances.entities.Transaction;
import com.application.finances.entities.User;
import com.application.finances.exceptions.ResourceNotFoundException; // Importado
import com.application.finances.services.TransactionService;
import com.application.finances.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    // Helper: Garante que pegamos o usuário ou lançamos erro 404
    private User getLoggedUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
    public ResponseEntity<Transaction> getById(@PathVariable Long id, Authentication authentication) {
        User user = getLoggedUser(authentication);
        return ResponseEntity.ok(service.findById(id, user));
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
    public ResponseEntity<Transaction> update(@PathVariable Long id,
                                              @RequestBody @Valid CreateTransactionDTO data,
                                              Authentication authentication) {
        User user = getLoggedUser(authentication);

        // O service.findById já vai validar se a transação existe e se pertence ao usuário
        Transaction transaction = service.findById(id, user);

        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setDate(data.date());
        transaction.setType(data.type());
        transaction.setCategory(data.category());

        service.save(transaction);

        return ResponseEntity.ok(transaction);
    }
}