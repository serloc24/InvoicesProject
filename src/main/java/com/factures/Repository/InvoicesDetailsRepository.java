package com.factures.Repository;

import com.factures.entities.InvoiceDetails;
import com.factures.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoicesDetailsRepository extends JpaRepository<InvoiceDetails, Long> {
     /*
    Automagically implemented by JpaRepository
        companyRepository.findAll()           // SELECT * FROM companies
        companyRepository.findById(1L)        // SELECT * WHERE id = 1
        companyRepository.save(company)       // INSERT o UPDATE
        companyRepository.deleteById(1L)      // DELETE WHERE id = 1
        companyRepository.existsById(1L)      // existe?
        companyRepository.count()             // COUNT(*)
     */

    List<InvoiceDetails> findByInvoice(Invoice invoice);
}
