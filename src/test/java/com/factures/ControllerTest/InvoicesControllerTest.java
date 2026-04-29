package com.factures.ControllerTest;

import com.factures.Controllers.InvoicesController;
import com.factures.Service.InvoiceService;
import com.factures.dto.request.CreateInvoiceDetailsRequest;
import com.factures.dto.request.CreateInvoiceRequest;
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

        InvoiceDetailsResponse detailsResponse1 = new InvoiceDetailsResponse(new ProductResponse(1L,"Toilet",BigDecimal.valueOf(30),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice1 = new InvoiceResponse(1L, LocalDate.now(),"ACCEPTED","Restoration",LocalDate.now(), BigDecimal.valueOf(21),1L,2L);

        InvoiceDetailsResponse detailsResponse2 = new InvoiceDetailsResponse(new ProductResponse(1L,"Bath",BigDecimal.valueOf(21),BigDecimal.valueOf(21)),2);
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"PENDING","Restoration",LocalDate.now(), BigDecimal.valueOf(21),1L,2L);
        Mockito.when(service.getAllInvoices()).thenReturn(List.of(invoice1,invoice2));

        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].state").value("ACCEPTED"));
    }

    @Test
    void getAllInvoicesOfAClient() throws Exception{
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21), 1L,2L);
        Mockito.when(service.getAllInvoicesByClient(1L)).thenReturn(List.of(invoice2));

        mockMvc.perform(get("/invoices/client/{clientId}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("Pending"));

    }

    @Test
    void getInvoiceById()throws Exception{
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21), 1L,2L);

        Mockito.when(service.getInvoiceById(1L)).thenReturn(invoice2);

        mockMvc.perform(get("/invoices/{invoiceId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Restoration"));

    }

    @Test
    void getInvoiceByCompany() throws Exception{
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21), 1L,2L);

        Mockito.when(service.getInvoicesByCompany(1L)).thenReturn(List.of(invoice2));

        mockMvc.perform(get("/invoices/company/{companyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").value(1L));
    }

    @Test
    void getInvoiceByState() throws Exception{
        InvoiceResponse invoice2 = new InvoiceResponse(1L, LocalDate.now(),"Pending","Restoration",LocalDate.now(), BigDecimal.valueOf(21), 1L,2L);

        Mockito.when(service.getInvoicesByState("Pending")).thenReturn(List.of(invoice2));

        mockMvc.perform(get("/invoices").param("state","Pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("Pending"));
    }

    @Test
    void createInvoice() throws Exception{
        CreateInvoiceDetailsRequest createInvoiceRequest = new CreateInvoiceDetailsRequest(1L,2L,2);
        CreateInvoiceDetailsRequest createInvoiceRequest2 = new CreateInvoiceDetailsRequest(1L,3L,3);
        CreateInvoiceRequest invoice2 = new CreateInvoiceRequest("Car fix", 1L,1L,List.of(createInvoiceRequest,createInvoiceRequest2));
        Mockito.when(service.createInvoice(Mockito.any(CreateInvoiceRequest.class))).thenReturn(new InvoiceResponse(1L,LocalDate.now(),"PAID","Car fix",LocalDate.now(),BigDecimal.valueOf(21),1L,2L));

        mockMvc.perform(post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice2)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("$.reason").value("Car fix"));

    }



}
