package com.mindhub.homebanking.dtos;

public class TransactionDateDTO {

    private String number;

    private int yearStart;

    private int monthStart;

    private int dayStart;

    private int yearEnd;

    private int monthEnd;

    private int dayEnd;


    public String getNumber() {
        return number;
    }

    public int getYearStart() {
        return yearStart;
    }

    public int getMonthStart() {
        return monthStart;
    }

    public int getDayStart() {
        return dayStart;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public int getMonthEnd() {
        return monthEnd;
    }

    public int getDayEnd() {
        return dayEnd;
    }
}
