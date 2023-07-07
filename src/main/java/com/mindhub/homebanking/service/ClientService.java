package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {


    public List<ClientDTO> getClientsDTO();

    public ClientDTO getClientDTO(Long id);

    public Client findByEmail(String email);

    public void saveClient(Client client);
}
