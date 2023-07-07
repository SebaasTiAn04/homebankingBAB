package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLoanServiceImplements implements ClientLoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan)
    {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public ClientLoanDTO getClientLoan(Long id) {
        return clientLoanRepository.findById(id).map(clientLoan -> new ClientLoanDTO(clientLoan)).orElse(null);
    }

    @Override
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanRepository.findAll().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
    }
}
