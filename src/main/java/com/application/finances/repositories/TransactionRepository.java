package com.application.finances.repositories;

import com.application.finances.entities.Transaction;
import com.application.finances.entities.User; // Não esqueça de importar o User
import com.application.finances.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Busca simples por usuário (para a listagem geral)
    List<Transaction> findByUser(User user);

    // Busca por período e usuario
    List<Transaction> findByDateBetweenAndUserOrderByDateDesc(LocalDate startDate, LocalDate endDate, User user);

    // Soma por tipo, data e usuario
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.date BETWEEN :startDate AND :endDate AND t.user = :user")
    BigDecimal getSumByTypeAndDate(@Param("type") TransactionType type,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate,
                                   @Param("user") User user);
}