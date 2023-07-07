package com.mindhub.homebanking.controller;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Enums.AccountType;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.utils.GenerateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }

    @GetMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClientsDTO();
    }

    private ClientDTO clientDTO = null;
    @GetMapping("/api/clients/current")
    public ClientDTO getClient(Authentication authentication){
        try{
            this.clientDTO = new ClientDTO(clientService.findByEmail(authentication.getName()));
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return this.clientDTO;
    }

    @PostMapping("/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("The first name field is empty", HttpStatus.FORBIDDEN);
        }
        else if(lastName.isEmpty()) {
            return new ResponseEntity<>("The last name field is empty", HttpStatus.FORBIDDEN);
        }else if(email.isEmpty()) {
            return new ResponseEntity<>("The email field is empty", HttpStatus.FORBIDDEN);
        }else if(password.isEmpty()) {
            return new ResponseEntity<>("The password field is empty", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        Account account = new Account("VIN-" + GenerateNumber.getRandomNumber(1000, 9999) , LocalDateTime.now(), 0.0 , true, AccountType.CHECKING);
        client.addAccount(account);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Registration was successful",HttpStatus.CREATED);
    }
}
