package com.mindhub.homebanking.dtos;

import java.util.ArrayList;
import java.util.List;

public class CreateLoanDTO {
    private String name;

    private double maxAmount;

    private double percentageInterest;

    public CreateLoanDTO() {
    }

    private List<Integer> payments = new ArrayList<>();

    public String getName() {
        return name;
    }

    public double getPercentageInterest() {
        return percentageInterest;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
