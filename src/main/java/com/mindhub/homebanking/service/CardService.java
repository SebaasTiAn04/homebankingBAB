package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Enums.CardColor;
import com.mindhub.homebanking.models.Enums.CardType;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CardService {

     void saveCard(Card card);

     Card findByNumber(String number);
     Card findByType(CardType type);

     CardDTO getCard( Long id);

     List<CardDTO> getCards();

    Card findByClientAndColorAndType(Client client, CardColor cardColor, CardType cardType );
}
