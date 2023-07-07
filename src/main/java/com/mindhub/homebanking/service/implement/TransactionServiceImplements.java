package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImplements implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction)
    {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findByDateBetween(Long id, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByDateBetween(id, start, end);
    }
}
