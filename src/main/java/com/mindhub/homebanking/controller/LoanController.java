package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.ApplicationLoanDTO;
import com.mindhub.homebanking.dtos.CreateLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.Enums.TransactionType;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LoanController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/loans")
    public List<LoanDTO> getLoansDTO() {
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> addLoan(Authentication authentication, @RequestBody ApplicationLoanDTO applicationLoanDTO){

        Client clientCurrent = clientService.findByEmail(authentication.getName());
        Loan loanExist = loanService.findById(applicationLoanDTO.getIdLoan());
        Account accountDestiny = accountService.findByNumber(applicationLoanDTO.getAccountDestiny());

        if(clientCurrent.equals(null)){
            return new ResponseEntity<>("Client is empty", HttpStatus.BAD_REQUEST);
        }

        if(applicationLoanDTO.equals(null)){
            return new ResponseEntity<>("Loan is empty", HttpStatus.BAD_REQUEST);
        }

        if (applicationLoanDTO.getAmount() < 1) {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.BAD_REQUEST);
        }

        if (applicationLoanDTO.getPayment() == 0) {
            return new ResponseEntity<>("Payments is empty", HttpStatus.BAD_REQUEST);
        }

        if(loanExist.equals(null)) {
            return new ResponseEntity<>("The loan does not exist", HttpStatus.BAD_REQUEST);
        }

        if(applicationLoanDTO.getAmount() > loanExist.getMaxAmount()) {
            return new ResponseEntity<>("The maximum loan amount cannot be exceeded", HttpStatus.BAD_REQUEST);
        }

        if(accountDestiny.equals(null)) {
            return new ResponseEntity<>("Destiny Account does not exist", HttpStatus.BAD_REQUEST);
        }

        if(!clientCurrent.getAccounts().contains(accountDestiny)) {
            return new ResponseEntity<>("Client does not Contain Account Selected", HttpStatus.BAD_REQUEST);
        }

        if(clientCurrent.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equals(loanExist.getName())).toArray().length >= 1) {
            return new ResponseEntity<>("The customer already has a loan of the selected type", HttpStatus.FORBIDDEN);
        }

        if(clientCurrent.getClientLoans().size() >= 3) {
            return new ResponseEntity<>("Client already has loan", HttpStatus.FORBIDDEN);
        }

        if(applicationLoanDTO.getIdLoan() > 0) {
            ClientLoan clientLoan = new ClientLoan(applicationLoanDTO.getAmount() * loanExist.getPercentageInterest(), applicationLoanDTO.getPayment(),LocalDateTime.now());
            clientLoan.setLoan(loanExist);
            clientLoan.setClient(clientCurrent);
            clientLoanService.saveClientLoan(clientLoan);
        }

        Transaction transaction = new Transaction(TransactionType.CREDIT,applicationLoanDTO.getAmount(), loanExist.getName() + "Loan Approved" + accountDestiny.getNumber(), LocalDateTime.now());
        transaction.setAccountAmount(accountDestiny.getBalance() - applicationLoanDTO.getAmount());
        accountDestiny.addTransaction(transaction);

        accountDestiny.setBalance(accountDestiny.getBalance() + applicationLoanDTO.getAmount());


        transactionService.saveTransaction(transaction);

        accountService.saveAccount(accountDestiny);
        return new ResponseEntity<>("successfully created",HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/api/loans/create")
    public ResponseEntity<Object> createLoan(@RequestBody CreateLoanDTO createLoanDTO)
    {
        if(createLoanDTO.getName().isEmpty()){
            return new ResponseEntity<>("Loan name is empty", HttpStatus.BAD_REQUEST);
        }
        if(createLoanDTO.getMaxAmount() < 1)
        {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (createLoanDTO.getPayments().isEmpty())
        {
            return new ResponseEntity<>("Payments is empty", HttpStatus.BAD_REQUEST);
        }

        Loan loan = new Loan(createLoanDTO.getName(),createLoanDTO.getMaxAmount(), createLoanDTO.getPercentageInterest(),createLoanDTO.getPayments() );
        loanService.saveLoan(loan);
        return new ResponseEntity<>("OK",HttpStatus.CREATED);
    }

}
