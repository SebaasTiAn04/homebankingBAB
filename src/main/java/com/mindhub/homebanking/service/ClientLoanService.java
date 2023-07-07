package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {

    public void saveClientLoan(ClientLoan clientLoan);

    public ClientLoanDTO getClientLoan(Long id);

    public List<ClientLoanDTO> getClientLoans();
}
