package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Enums.CardColor;
import com.mindhub.homebanking.models.Enums.CardType;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplements implements CardService {


    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card)
    {
        cardRepository.save(card);
    }

    @Override
    public Card findByNumber(String number) {
        return  cardRepository.findByNumber(number);
    }

    @Override
    public Card findByType(CardType type) {
        return  cardRepository.findByType(type);
    }

    @Override
    public CardDTO getCard(Long id) {
        return  cardRepository.findById(id).map(card -> new CardDTO(card)).orElse(null);
    }

    @Override
    public List<CardDTO> getCards() {
        return  cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(toList());
    }

    @Override
    public Card findByClientAndColorAndType(Client client, CardColor cardColor, CardType cardType ) {
        return cardRepository.findByClientAndColorAndType(client, cardColor, cardType);
    }
}
