package com.mindhub.homebanking.dtos;

import java.util.List;

public class ApplicationLoanDTO {
    private Long idLoan;

    private double amount;

    private int payment;

    private String accountDestiny;


    public ApplicationLoanDTO (){}

    public Long getIdLoan() {
        return idLoan;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }
}
