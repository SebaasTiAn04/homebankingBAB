package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    public void saveTransaction(Transaction transaction);

    List<Transaction> findByDateBetween(@Param("id") Long id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
