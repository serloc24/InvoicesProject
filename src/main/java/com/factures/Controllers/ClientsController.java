package com.factures.Controllers;


import com.factures.Service.ClientsService;
import com.factures.dto.mapper.ClientMapper;
import com.factures.dto.request.CreateClientRequest;
import com.factures.dto.response.ClientResponse;
import com.factures.entities.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    private ClientsService clientsService;
    public ClientsController(ClientsService clientsService, ClientMapper clientMapper) {
        this.clientsService = clientsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getByClientId(@PathVariable Long id){
       return ResponseEntity.ok(clientsService.getClientById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<ClientResponse> getClientByName(@RequestParam String name){
        return ResponseEntity.ok(clientsService.getClientByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients(){
        return ResponseEntity.ok(clientsService.getAllClients());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest newClient){
        ClientResponse response = clientsService.createClient(newClient);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

}
