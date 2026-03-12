package com.factures.Repository;

import com.factures.entities.Client;
import com.factures.entities.Company;
import com.factures.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoice, Long> {
     /*
    Automagically implemented by JpaRepository
        companyRepository.findAll()           // SELECT * FROM companies
        companyRepository.findById(1L)        // SELECT * WHERE id = 1
        companyRepository.save(company)       // INSERT o UPDATE
        companyRepository.deleteById(1L)      // DELETE WHERE id = 1
        companyRepository.existsById(1L)      // existe?
        companyRepository.count()             // COUNT(*)
     */

    List<Invoice> findByClient(Client client);
    List<Invoice> findByCompany(Company company);
    List<Invoice> findByState(String state);
}
