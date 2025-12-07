package com.application.finances.controllers;

import com.application.finances.dtos.CreateTransactionDTO;
import com.application.finances.dtos.MonthlySummaryDTO;
import com.application.finances.entities.Transaction;
import com.application.finances.entities.User;
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

    // Helper: Metodo privado para pegar o usuario logado e evitar repeticao de codigo
    private User getLoggedUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // --- Resumo Mensal ---
    @GetMapping
    public ResponseEntity<MonthlySummaryDTO> getSummary(@RequestParam(required = false) Integer year,
                                                        @RequestParam(required = false) Integer month,
                                                        Authentication authentication) {
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();

        User user = getLoggedUser(authentication); // Pega o usuario do token

        // Passa o usuário para o servico filtrar os dados
        return ResponseEntity.ok(service.getMonthlySummary(year, month, user));
    }

    // --- Buscar transacao por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id, Authentication authentication) {
        User user = getLoggedUser(authentication);
        return ResponseEntity.ok(service.findById(id, user));
    }

    // --- Deletar transacao ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        User user = getLoggedUser(authentication);
        service.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    // --- Criar nova transacao  ---
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateTransactionDTO data, Authentication authentication) {
        User user = getLoggedUser(authentication);

        Transaction newTransaction = new Transaction();
        newTransaction.setDescription(data.description());
        newTransaction.setAmount(data.amount());
        newTransaction.setDate(data.date());
        newTransaction.setType(data.type());
        newTransaction.setCategory(data.category());

        // Vínculo obrigatório com o usuário logado
        newTransaction.setUser(user);

        service.save(newTransaction);

        return ResponseEntity.status(201).build();
    }
}