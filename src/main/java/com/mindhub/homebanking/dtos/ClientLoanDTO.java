package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

import java.time.LocalDateTime;

public class ClientLoanDTO {

    private long id;

    private long loanId;
    private String loanName;

    private double amount;

    private int payments;

    private LocalDateTime date;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.date = clientLoan.getDate();

    }

    public long getId() {
        return id;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
