package com.factures.ControllerTest;

import com.factures.Controllers.CompanyController;
import com.factures.Service.CompanyService;
import com.factures.entities.Company;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CompanyService service;

    @Test
    void getAllCompanies() throws Exception{
        Company company1 =  new Company("Endesa","endesa@endesa.com","Calle endesa");
        Company company2 =  new Company("Iberdrola","Iberdrola@Iberdrola.com","Calle Iberdrola");

        Mockito.when(service.getAllCompanies()).thenReturn(List.of(company1,company2));

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getCompanyByName() throws Exception{
        Company company1 =  new Company("Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.getCompanyByName("Endesa")).thenReturn(company1);

        mockMvc.perform(get("/companies").param("name", "Endesa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Endesa"));
    }

    @Test
    void getCompanyById() throws Exception{
        Company company1 =  new Company("Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.getCompanyById(1L)).thenReturn(company1);

        mockMvc.perform(get("/companies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Endesa"));
    }

    @Test
    void createCompany() throws Exception{
        Company company1 =  new Company("Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.createCompany(Mockito.any(Company.class))).thenReturn(company1);

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(company1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Endesa"))
                .andExpect(header().exists("location"));
    }

}
