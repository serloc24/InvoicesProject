package com.factures;

import com.factures.Repository.*;
import com.factures.entities.Invoices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class FacturesApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private InvoicesDetailsRepository invoicesDetailsRepository;

    @Autowired
    private ProductsRepository productsRepository;

	@Test
	void testDatabaseConnection() throws Exception{
        assertNotNull(dataSource);

        try(Connection connection = dataSource.getConnection()){
            assertTrue(connection.isValid(2));
            System.out.println("Connection to DB established");
        }
	}

    @Test
    void testDataBaseData(){
        //Companies Repository
        assertEquals(2, companiesRepository.count());

        //Clients Repository
        assertEquals(3, clientsRepository.count());

        //InvoicesDetails && Invoices Repository
        Invoices invoice = invoicesRepository.findById(1L).get();
        invoicesDetailsRepository.findByInvoice(invoice);
        assertEquals(1L,invoice.getId());

        //Products Repository
        assertEquals(5, productsRepository.count());

    }

}
