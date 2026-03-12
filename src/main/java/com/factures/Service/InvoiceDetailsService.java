package com.factures.Service;

import com.factures.Repository.InvoicesDetailsRepository;
import com.factures.entities.InvoiceDetails;
import com.factures.entities.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoicesDetailsRepository invoicesDetailsRepository;

    public InvoiceDetails createInvoiceDetail(InvoiceDetails invoiceDetails){
        return invoicesDetailsRepository.save(invoiceDetails);
    }

    public InvoiceDetails getInvoiceDetailById(Long id){
        return invoicesDetailsRepository.findById(id).orElseThrow();
    }

    public List<InvoiceDetails> getAllInvoicesDetailsByInvoice(Invoice invoice){
        return invoicesDetailsRepository.findByInvoice(invoice);
    }

    public InvoiceDetails updateInvoiceDetail(Long invoiceDetailsId, InvoiceDetails updatedInvoiceDetails){
        InvoiceDetails theInvoiceDetail = invoicesDetailsRepository.findById(invoiceDetailsId).orElseThrow(() -> new RuntimeException("InvoiceDetailsId not found to update"));
        if(theInvoiceDetail.getId() == updatedInvoiceDetails.getId()){
            theInvoiceDetail.setAmount(updatedInvoiceDetails.getAmount());
            return invoicesDetailsRepository.save(theInvoiceDetail);
        }
        throw new IllegalArgumentException("Both invoicesDetailsId are not the same to update");

    }

    public void deleteInvoiceDetail(InvoiceDetails invoiceDetails){
        invoicesDetailsRepository.delete(invoiceDetails);
    }

}
