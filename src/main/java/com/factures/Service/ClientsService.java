package com.factures.Service;

import com.factures.Repository.ClientsRepository;
import com.factures.dto.mapper.ClientMapper;
import com.factures.dto.request.CreateClientRequest;
import com.factures.dto.request.UpdateClientRequest;
import com.factures.dto.response.ClientResponse;
import com.factures.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientsService {
    private ClientsRepository clientsRepository;
    private ClientMapper clientMapper;

    //Constructor injection
    public ClientsService(ClientsRepository clientsRepository, ClientMapper clientMapper) {
        this.clientsRepository = clientsRepository;
        this.clientMapper = clientMapper;
    }
    @Transactional
    public ClientResponse createClient(CreateClientRequest requestClient) {
        if (clientsRepository.findByEmail(requestClient.email()).isPresent()) {
            throw new IllegalArgumentException("This email already exists");
        }
        Client client = clientMapper.createToEntity(requestClient);
        Client saved = clientsRepository.save(client);
        return clientMapper.entityToDTO(saved);
    }

    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientsRepository.findAll();
        return clientMapper.entitiesToDTOList(clients);

    }

    public ClientResponse getClientById(Long id) {
        Client theClient = clientsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This ClientId does not exist"));
        return clientMapper.entityToDTO(theClient);
    }

    public ClientResponse getClientByName(String name){
        Client theClient = clientsRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Nothing found for this Client Name"));
        return clientMapper.entityToDTO(theClient);
    }

    @Transactional
    public ClientResponse updateClient(UpdateClientRequest toUpdateClient){
        if(clientsRepository.existsById(toUpdateClient.id())){
            Client theClient = clientMapper.updateToEntity(toUpdateClient);
            return clientMapper.entityToDTO(clientsRepository.save(theClient));
        }
        throw new IllegalArgumentException("Can't update a Client if not exists");
    }



}
