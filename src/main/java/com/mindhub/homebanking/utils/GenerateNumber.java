package com.mindhub.homebanking.utils;

public class GenerateNumber {

    public static String getCardNumber() {
        return getRandomNumber(1000, 10000) + "-" + getRandomNumber(1000, 10000) + "-" + getRandomNumber(1000, 10000) + "-" + getRandomNumber(1000, 10000);
    }

    public static int getCardCVV() {
        int cardCVV = getRandomNumber(100, 1000);
        return cardCVV;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
