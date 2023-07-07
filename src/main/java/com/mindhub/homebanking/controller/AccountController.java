package com.mindhub.homebanking.controller;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Enums.AccountType;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.utils.GenerateNumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/api/accounts/{id}")
    public AccountDTO getClient(@PathVariable Long id){
        return accountService.getAccountDTO(id);
    }

    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccountsDTO();
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> create(Authentication authentication, @RequestParam AccountType type) {

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if(type.equals(AccountType.EMPTY)) {
            return new ResponseEntity<>("you must select an account type", HttpStatus.BAD_REQUEST);
        }

        if(clientCurrent.getAccounts().stream().filter(account -> account.getActive() == true).collect(Collectors.toSet()).size() == 3) {
            return new ResponseEntity<>("Max amount of Accounts", HttpStatus.FORBIDDEN);
        }

        Account accountNew = new Account("VIN" + GenerateNumber.getRandomNumber(100000,10000000), LocalDateTime.now(), 0.0, true, type );
        clientCurrent.addAccount(accountNew);
        accountService.saveAccount(accountNew);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/clients/current/accounts/delete")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number){
        Account deletedAccount = accountService.findByNumber(number);
        if (deletedAccount.getBalance() > 0) {
            return new ResponseEntity<>("Account balance is bigger than $0", HttpStatus.FORBIDDEN);
        }
        deletedAccount.setActive(false);
        accountService.saveAccount(deletedAccount);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

}
