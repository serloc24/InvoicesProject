package com.factures.Service;

import com.factures.Repository.ClientsRepository;
import com.factures.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository clientsRepository;

    public Client createClient(Client client){
        if(clientsRepository.findByEmail(client.getEmail()).isPresent()){
            throw new IllegalArgumentException("This email already exists");
        }
        return clientsRepository.save(client);
    }

    public List<Client> getAllClients(){
        return clientsRepository.findAll();
    }
    public Client getClientById(Long id){
        return clientsRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("This ClientId does not exist"));}

    public Client getClientByName(String name){
        return clientsRepository.findByName(name)
                                .orElseThrow(() -> new IllegalArgumentException("Nothing found for this Client Name"));
    }

    public Client updateClient(Client client){
        if(clientsRepository.existsById(client.getId())){
            return clientsRepository.save(client);
        }
        throw new IllegalArgumentException("Can't update a Client if not exists");
    }



}
