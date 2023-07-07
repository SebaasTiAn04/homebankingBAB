package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientLoanController {

    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/api/clientLoans/{id}")
    public ClientLoanDTO getClientLoan(@PathVariable Long id){
        return clientLoanService.getClientLoan(id);
    }

    @GetMapping("/api/clientLoans")
    public List<ClientLoanDTO> getClientLoans(){
        return clientLoanService.getClientLoans();
    }
}
