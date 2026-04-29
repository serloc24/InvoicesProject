package com.factures.ControllerTest;

import com.factures.Controllers.CompanyController;
import com.factures.Service.CompanyService;
import com.factures.dto.mapper.CompanyMapper;
import com.factures.dto.request.CreateCompanyRequest;
import com.factures.dto.response.CompanyResponse;
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

    @MockitoBean
    private CompanyMapper mapper;

    @Test
    void getAllCompanies() throws Exception{
        CompanyResponse company1 =  new CompanyResponse(1L,"Endesa","endesa@endesa.com","Calle endesa");
        CompanyResponse company2 =  new CompanyResponse(2L,"Iberdrola","Iberdrola@Iberdrola.com","Calle Iberdrola");

        Mockito.when(service.getAllCompanies()).thenReturn(List.of(company1,company2));

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getCompanyByName() throws Exception{
        CompanyResponse company1 =  new CompanyResponse(1L,"Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.getCompanyByNameOrEmail("Endesa", null)).thenReturn(company1);

        mockMvc.perform(get("/companies/search").param("name", "Endesa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Endesa"));
    }

    @Test
    void getCompanyByEmail() throws Exception{
        CompanyResponse company1 =  new CompanyResponse(1L,"Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.getCompanyByNameOrEmail(null,"endesa@endesa.com")).thenReturn(company1);

        mockMvc.perform(get("/companies/search").param("email", "endesa@endesa.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Endesa"));
    }

    @Test
    void getCompanyById() throws Exception{
        CompanyResponse company1 =  new CompanyResponse(1L,"Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.getCompanyById(1L)).thenReturn(company1);

        mockMvc.perform(get("/companies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Endesa"));
    }

    @Test
    void createCompany() throws Exception{
        CreateCompanyRequest company1 =  new CreateCompanyRequest("Endesa","endesa@endesa.com","Calle endesa");

        Mockito.when(service.createCompany(company1)).thenReturn(new CompanyResponse(1L,"Endesa","endesa@endesa.com", "Street endesa"));


        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(company1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Endesa"))
                .andExpect(header().exists("location"));
    }

}
