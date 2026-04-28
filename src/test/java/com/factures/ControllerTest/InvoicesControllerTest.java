package com.factures.ControllerTest;

import com.factures.Controllers.InvoicesController;
import com.factures.Service.InvoiceService;
import com.factures.dto.response.*;
import com.factures.entities.Client;
import com.factures.entities.Company;
import com.factures.entities.Invoice;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoicesController.class)
public class InvoicesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InvoiceService service;

    @Test
    void getAllInvoices() throws Exception {

        InvoiceDetailsResponse detailsResponse1 = new InvoiceDetailsResponse(new ProductResponse("Toilet",BigDecimal.valueOf(30),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice1 = new InvoiceResponse(1L, LocalDate.now(),"ACCEPTED","Restoration",LocalDate.now(), BigDecimal.valueOf(21),
                new CompanyResponse("RestoredSL","restorations@restorations.com","Restoration Street"),
                new ClientResponse(1L,"Juan","juan@juan.com","Juan Street"),
                List.of(detailsResponse1));

        InvoiceDetailsResponse detailsResponse2 = new InvoiceDetailsResponse(new ProductResponse("Bath",BigDecimal.valueOf(21),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"PENDING","Restoration",LocalDate.now(), BigDecimal.valueOf(21),
                new CompanyResponse("RestoredSL","restorations@restorations.com","Restoration Street"),
                new ClientResponse(1L,"Paco","paco@paco.com","Paco Street"),
                List.of(detailsResponse2));
        Mockito.when(service.getAllInvoices()).thenReturn(List.of(invoice1,invoice2));

        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].state").value("ACCEPTED"));
    }

    @Test
    void getAllInvoicesOfAClient() throws Exception{
        InvoiceDetailsResponse detailsResponse = new InvoiceDetailsResponse(new ProductResponse("Bath",BigDecimal.valueOf(21),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21),
                                                        new CompanyResponse("RestoredSL","restorations@restorations.com","Restoration Street"),
                                                        new ClientResponse(1L,"Paco","paco@paco.com","Paco Street"),
                                                        List.of(detailsResponse));
        Mockito.when(service.getAllInvoicesByClient(1L)).thenReturn(List.of(invoice2));

        mockMvc.perform(get("/invoices/client/{clientId}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("PAID"));

    }

    @Test
    void getInvoiceById()throws Exception{
        InvoiceDetailsResponse detailsResponse = new InvoiceDetailsResponse(new ProductResponse("Bath",BigDecimal.valueOf(21),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21),
                new CompanyResponse("RestoredSL","restorations@restorations.com","Restoration Street"),
                new ClientResponse(1L,"Paco","paco@paco.com","Paco Street"),
                List.of(detailsResponse));
        Mockito.when(service.getInvoiceById(1L)).thenReturn(invoice2);

        mockMvc.perform(get("/invoices/{invoiceId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Car fix"));

    }

    @Test
    void getInvoiceByCompany() throws Exception{
        InvoiceDetailsResponse detailsResponse = new InvoiceDetailsResponse(new ProductResponse("Bath",BigDecimal.valueOf(21),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21),
                new CompanyResponse("RestoredSL","restorations@restorations.com","Restoration Street"),
                new ClientResponse(1L,"Paco","paco@paco.com","Paco Street"),
                List.of(detailsResponse));
        Mockito.when(service.getInvoicesByCompany(1L)).thenReturn(List.of(invoice2));

        mockMvc.perform(get("/invoices/company/{companyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].company.name").value("Mechanics"));
    }

    @Test
    void getInvoiceByState() throws Exception{
        InvoiceDetailsResponse detailsResponse = new InvoiceDetailsResponse(new ProductResponse("Bath",BigDecimal.valueOf(21),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21),
                new CompanyResponse("RestoredSL","restorations@restorations.com","Restoration Street"),
                new ClientResponse(1L,"Paco","paco@paco.com","Paco Street"),
                List.of(detailsResponse));
        Mockito.when(service.getInvoicesByState("PAID")).thenReturn(List.of(invoice2));

        mockMvc.perform(get("/invoices").param("state","PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("PAID"));
    }

    @Test
    void createInvoice() throws Exception{
        Invoice invoice2 = new Invoice("PAID", "Car fix", new Company("Mechanics", "car@car.com","Mechanics Address"),new Client("Luis", "Luis@Luis.com","Luis Street"));
        Mockito.when(service.createInvoice(Mockito.any(Invoice.class))).thenReturn(invoice2);

        mockMvc.perform(post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice2)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("$.reason").value("Car fix"));

    }



}
