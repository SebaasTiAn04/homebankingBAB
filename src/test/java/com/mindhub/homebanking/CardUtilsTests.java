package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.utils.GenerateNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests{

//    @Autowired
//    CardRepository cardRepository;
//
//    @Test
//    public void cardNumberIsCreated(){
//        String cardNumber = GenerateNumber.getCardNumber();
//        assertThat(cardNumber,is(not(emptyOrNullString())));
//    }
//
//    @Test
//    public void cardCVVIsCreated(){
//        int cardCVV = GenerateNumber.getCardCVV();
//        assertThat(cardCVV, notNullValue());
//    }
//
//    @Test
//    public void existCard(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards,is(not(empty())));
//    }
}
