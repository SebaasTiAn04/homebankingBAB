package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    public List<AccountDTO> getAccountsDTO();

    public AccountDTO getAccountDTO(Long id);

    public Account findByNumber(String number);

    public void saveAccount(Account account);

}
