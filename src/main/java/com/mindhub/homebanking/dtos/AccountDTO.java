package com.mindhub.homebanking.dtos;

import com.lowagie.text.pdf.PdfPCell;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Enums.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;

    private String number;

    private LocalDateTime creationDate;

    private double balance;

    private AccountType type;

    private boolean active;

    private Set<TransactionDTO> transactionDTO;


    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.type = account.getType();
        this.active = account.getActive();
        this.transactionDTO = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public Set<TransactionDTO> getTransactionDTO() {
        return transactionDTO;
    }
}

