package com.factures.Service;

import com.factures.Repository.ClientsRepository;
import com.factures.Repository.InvoicesRepository;
import com.factures.entities.Client;
import com.factures.entities.Company;
import com.factures.entities.Invoice;
import com.factures.entities.InvoiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    public List<Invoice> getAllInvoices() { return invoicesRepository.findAll(); }
    public List<Invoice> getAllInvoicesByClient(Long clientId){
        Client theClient = clientsService.getClientById(clientId);
        return invoicesRepository.findByClient(theClient);
    }
    public Invoice getInvoiceById(Long id){ return invoicesRepository.findById(id).orElseThrow(); }
    public List<Invoice> getInvoicesByCompany(Long companyId){
        Company theCompany = companyService.getCompanyById(companyId);
        return invoicesRepository.findByCompany(theCompany);
    }
    public List<Invoice> getInvoicesByState(String state){ return invoicesRepository.findByState(state); }


    public Invoice createInvoice(Invoice invoice){
        //Retrieve all the company and client objects
        Client theClient = clientsService.getClientById(invoice.getClient().getId());
        Company theCompany = companyService.getCompanyById(invoice.getCompany().getId());

        invoice.setClient(theClient);
        invoice.setCompany(theCompany);

        //Persist the Lines inside the invoice
        for(InvoiceDetails detail : invoice.getLines()){
            invoiceDetailsService.createInvoiceDetail(detail);
        }

        return invoicesRepository.save(invoice);
    }

    public Invoice updateInvoice(Invoice updatedInvoice, Long id){
        Invoice theInvoice = invoicesRepository.findById(updatedInvoice.getId()).orElseThrow(() -> new RuntimeException("Didn't find the invoice to update"));
        if(theInvoice.getId() == id){
            return invoicesRepository.save(theInvoice);
        }
        throw new IllegalArgumentException("The id in the object is not the same you want to update");
    }

    public Invoice updateState(Long id, String newState){
        Invoice theInvoice = invoicesRepository.findById(id).orElseThrow(() -> new RuntimeException("Didn't find the invoice to update"));
        theInvoice.setState(newState);
        return invoicesRepository.save(theInvoice);
    }

    public void deleteInvoice(Long invoiceId){
        Invoice theInvoice =  invoicesRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("InvoiceId to delete not found"));
        invoicesRepository.delete(theInvoice);
    }


}
