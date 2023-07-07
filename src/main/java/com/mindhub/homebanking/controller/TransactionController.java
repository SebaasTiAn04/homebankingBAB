package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Enums.TransactionType;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;


    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> createTransaction (Authentication authentication,
                                                     @RequestParam double amount,
                                                     @RequestParam String description,
                                                     @RequestParam String accountO, @RequestParam String accountD) throws Exception {

        Client client = clientService.findByEmail(authentication.getName());
        Account accountOrigin = accountService.findByNumber(accountO);
        Account accountDestin = accountService.findByNumber(accountD);
        Set<Account> accountExist = client.getAccounts().stream().filter(account -> account.getNumber().equals(accountO)).collect(Collectors.toSet());

        if (amount <= 0) {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.EXPECTATION_FAILED);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("description is empty", HttpStatus.FORBIDDEN);
        }
        if (accountO.isEmpty() ) {
            return new ResponseEntity<>("The origin account is empty", HttpStatus.FORBIDDEN);
        }
        if (accountD.isEmpty()) {
            return new ResponseEntity<>("The destination account is empty", HttpStatus.FORBIDDEN);
        }

        if (accountO.equals(accountD)){
            return new ResponseEntity<>("Origin Account cant be the same as Destiny Account", HttpStatus.FORBIDDEN);
        }
        if(accountExist.isEmpty()){
            return new ResponseEntity<>("Your origin account doesent exist", HttpStatus.FORBIDDEN);
        }

        if(accountDestin == null){
            return new ResponseEntity<>("Your Destiny Account doesent exist", HttpStatus.FORBIDDEN);
        }

        if(accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("Not enough balance", HttpStatus.FORBIDDEN);
        }

        Transaction transactionOrigin = new Transaction( TransactionType.DEBIT, amount, description + " - Account: " + accountDestin.getNumber(), LocalDateTime.now());
        Transaction transactionDestin = new Transaction(TransactionType.CREDIT, amount, description + " - Account: " + accountOrigin.getNumber(), LocalDateTime.now());

        transactionOrigin.setAccountAmount(accountDestin.getBalance() - amount);

        accountOrigin.addTransaction(transactionOrigin);
        accountDestin.addTransaction(transactionDestin);

        accountOrigin.setBalance(accountOrigin.getBalance() - amount);
        accountDestin.setBalance(accountDestin.getBalance() + amount);

        transactionService.saveTransaction(transactionOrigin);
        transactionService.saveTransaction(transactionDestin);

        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountDestin);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}


