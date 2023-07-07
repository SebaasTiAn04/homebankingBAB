package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.Enums.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;

    private TransactionType type;

    private double amount;

    private String description;

    private double accountAmount;

    private LocalDateTime date;


    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.accountAmount = transaction.getAccountAmount();
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }


}
