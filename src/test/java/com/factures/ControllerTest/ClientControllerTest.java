package com.factures.ControllerTest;

import com.factures.Controllers.ClientsController;
import com.factures.Service.ClientsService;
import com.factures.entities.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClientsController.class)
public class ClientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientsService service;

    @Test
    void getAllClients() throws Exception{
        Client client1 = new Client("Pedro", "pedro@pedro.com","Calle Pedro");
        Client client2 = new Client("Pascual", "pascual@pascual.com","Calle Pascual");
        Mockito.when(service.getAllClients()).thenReturn(List.of(client1, client2));
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createClient() throws Exception {
        Client client1 = new Client(1L,"Pedro", "pedro@pedro.com","Calle Pedro");
        Mockito.when(service.createClient(Mockito.any(Client.class))).thenReturn(client1);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pedro"));
    }

    @Test
    void getAClientById() throws Exception{
        Client client1 = new Client(1L, "Pedro", "pedro@pedro.com","Calle Pedro");
        Mockito.when(service.getClientById(1L)).thenReturn(client1);

        mockMvc.perform(get("/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pedro"));
    }

    @Test
    void getAClientByName() throws Exception{
        Client client1 = new Client(1L, "Pedro", "pedro@pedro.com","Calle Pedro");
        Mockito.when(service.getClientByName("Pedro")).thenReturn(client1);

        mockMvc.perform(get("/clients").param("name","Pedro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pedro"));

    }


}
