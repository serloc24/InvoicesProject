package com.factures.ControllerTest;

import com.factures.Controllers.ProductController;
import com.factures.Service.ProductsService;
import com.factures.entities.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductsService service;

    @Test
    void getAllProducts() throws Exception{
        Product product1 = new Product("Pc", new BigDecimal("22.0"), new BigDecimal("21.0"));
        Product product2 = new Product("Tv", new BigDecimal("243.43"), new BigDecimal("21.0"));

        Mockito.when(service.getAllProducts()).thenReturn(List.of(product1,product2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("Pc"));
    }

    @Test
    void getProductById() throws Exception{
        Product product1 = new Product("Pc", 22.1, 21);
        Mockito.when(service.getProductById(1L)).thenReturn(product1);

        mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Pc"));
    }

    @Test
    void createProduct() throws Exception{
        Product product1 = new Product("Pc", 22.1, 21);
        Mockito.when(service.createProduct(Mockito.any(Product.class))).thenReturn(product1);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("$.description").value("Pc"));

    }


}
