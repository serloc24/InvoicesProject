package com.factures.Controllers;


import com.factures.Service.ClientsService;
import com.factures.entities.Client;
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

    @Autowired
    private ClientsService clientsService;

    @GetMapping("/{id}")
    public ResponseEntity<Client> getByClientId(@PathVariable Long id){
       return ResponseEntity.ok( clientsService.getClientById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<Client> getClientByName(@RequestParam String name){
        return ResponseEntity.ok(clientsService.getClientByName(name));
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity.ok(clientsService.getAllClients());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Client> createClient(@RequestBody Client newClient){
        Client theClient = clientsService.createClient(newClient);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(theClient.getId())
                .toUri();
        return ResponseEntity.created(location).body(theClient);
    }

}
