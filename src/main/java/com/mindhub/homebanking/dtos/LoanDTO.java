package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanDTO {

    private long id;

    private String name;

    private double maxAmount;

    private double percentageInterest;
    private List<Integer> payments = new ArrayList<Integer>();


    private Set<ClientLoan> clientLoans = new HashSet<>();

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.percentageInterest = loan.getPercentageInterest();
        this.payments = loan.getPayments();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getPercentageInterest() {
        return percentageInterest;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public java.util.List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(java.util.List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClient() {
        return clientLoans;
    }
}
