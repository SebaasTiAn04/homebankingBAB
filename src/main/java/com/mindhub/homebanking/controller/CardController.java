package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Enums.CardColor;
import com.mindhub.homebanking.models.Enums.CardType;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.utils.GenerateNumber;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/api/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardService.getCard(id);
    }

    @GetMapping("/api/cards")
    public List<CardDTO> getCards(){
        return cardService.getCards();
    }

    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> create(Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color){
        Client clientCurrent = clientService.findByEmail(authentication.getName());

        Set<Card> cardsActive = clientCurrent.getCards().stream().filter(card -> card.isActive() == true).collect(Collectors.toSet());

        if (cardService.findByClientAndColorAndType(clientCurrent,color,type ) != null){
            return new ResponseEntity<>("You already have this card", HttpStatus.FORBIDDEN);
        }

        if(type.equals(CardType.EMPTY)) {
            return new ResponseEntity<>("You must select a type of card", HttpStatus.FORBIDDEN);
        }

        if(color.equals(CardColor.EMPTY)) {
            return new ResponseEntity<>("You must select a card color", HttpStatus.FORBIDDEN);
        }

        if (type.equals(CardType.CREDIT)) {
            if ((cardsActive.stream().filter(card -> card.getType() == CardType.CREDIT).collect(Collectors.toSet()).size() == 3)) {
                return new ResponseEntity<>("You can have a maximum of 3 cards per type", HttpStatus.FORBIDDEN);
            }
        }

        if (type.equals(CardType.DEBIT)) {
            if (cardsActive.stream().filter(card -> card.getType() == CardType.DEBIT).collect(Collectors.toSet()).size()==3) {
                return new ResponseEntity<>("You can have a maximum of 3 cards per type", HttpStatus.FORBIDDEN);
            }
        }

        Card cardNew = new Card(type, GenerateNumber.getCardNumber() ,color,clientCurrent.getFirstName() + " " + clientCurrent.getLastName(), GenerateNumber.getCardCVV(),  LocalDate.now(), LocalDate.now().plusYears(5),true);
        clientCurrent.addCard(cardNew);
        cardService.saveCard(cardNew);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/clients/current/cards/delete")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number){
        Card deletedCard = cardService.findByNumber(number);
        deletedCard.setActive(false);
        cardService.saveCard(deletedCard);
        return new ResponseEntity<>("Card deleted", HttpStatus.OK);
    }


}
