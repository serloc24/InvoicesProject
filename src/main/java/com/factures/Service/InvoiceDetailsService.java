package com.factures.Service;

import com.factures.Repository.InvoicesDetailsRepository;
import com.factures.dto.mapper.InvoiceDetailsMapper;
import com.factures.dto.request.CreateInvoiceDetailsRequest;
import com.factures.dto.response.InvoiceDetailsResponse;
import com.factures.entities.InvoiceDetails;
import com.factures.entities.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailsService {

    private final InvoiceDetailsMapper invoiceDetailsMapper;
    private final InvoicesDetailsRepository invoicesDetailsRepository;

    public InvoiceDetailsService(InvoiceDetailsMapper invoiceDetailsMapper, InvoicesDetailsRepository invoicesDetailsRepository) {
        this.invoiceDetailsMapper = invoiceDetailsMapper;
        this.invoicesDetailsRepository = invoicesDetailsRepository;
    }

    public InvoiceDetailsResponse createInvoiceDetail(CreateInvoiceDetailsRequest invoiceDetailsRequest, Invoice invoice){
        InvoiceDetails theInvoiceDetails = invoiceDetailsMapper.createToEntity(invoiceDetailsRequest);
        theInvoiceDetails = invoicesDetailsRepository.save(theInvoiceDetails);
        return invoiceDetailsMapper.entityToDTO(theInvoiceDetails);
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
