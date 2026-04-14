package com.factures.dto.mapper;

import com.factures.dto.request.CreateClientRequest;
import com.factures.dto.request.UpdateClientRequest;
import com.factures.dto.response.ClientResponse;
import com.factures.entities.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper{

    public Client updateToEntity(UpdateClientRequest request){
        return new Client(request.id(), request.name(), request.email(), request.address());
    }

    public Client createToEntity(CreateClientRequest request){
        return new Client(request.name(), request.email(), request.address());
    }

    public ClientResponse entityToDTO(Client client){
        return new ClientResponse(client.getId(), client.getName(), client.getEmail(), client.getAddress());
    }

    public List<ClientResponse> entitiesToDTOList(List<Client> clients) {
        return clients.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
