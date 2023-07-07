package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Enums.CardColor;
import com.mindhub.homebanking.models.Enums.CardType;
import java.time.LocalDate;

public class CardDTO {
    private long id;

    private CardType type;
    private String number;

    private CardColor color;
    private String cardHolder;
    private long cvv;

    private boolean active;
    private LocalDate thruDate;
    private LocalDate fromDate;


    public CardDTO(Card card) {
        this.id = card.getId();
        this.type = card.getType();
        this.number = card.getNumber();
        this.color = card.getColor();
        this.cardHolder = card.getCardHolder();
        this.cvv = card.getCvv();
        this.active = card.isActive();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
    }

    public long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public CardColor getColor() {
        return color;
    }

    public boolean isActive() {
        return active;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public long getCvv() {
        return cvv;
    }


    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
}
