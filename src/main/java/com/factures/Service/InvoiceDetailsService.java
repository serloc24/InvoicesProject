package com.factures.Service;

import com.factures.Repository.InvoicesDetailsRepository;
import com.factures.dto.mapper.InvoiceDetailsMapper;
import com.factures.dto.request.CreateInvoiceDetailsRequest;
import com.factures.dto.request.UpdateInvoiceDetailsRequest;
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

    public InvoiceDetailsResponse getInvoiceDetailById(Long id){
        InvoiceDetails theInvoiceDetail = invoicesDetailsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("InvoiceDetail ID not found"));
        return invoiceDetailsMapper.entityToDTO(theInvoiceDetail);
    }

    public List<InvoiceDetailsResponse> getAllInvoicesDetailsByInvoice(Invoice invoice){
        List<InvoiceDetails> allInvoiceDetails = invoicesDetailsRepository.findByInvoice(invoice);
        return invoiceDetailsMapper.entitiesToDTOList(allInvoiceDetails);
    }

    public InvoiceDetailsResponse updateInvoiceDetail(Long invoiceDetailsId, UpdateInvoiceDetailsRequest request){
        InvoiceDetails theInvoiceDetails = invoicesDetailsRepository.findById(invoiceDetailsId)
                                                                    .orElseThrow(() -> new RuntimeException("InvoiceDetailsId not found to update"));
        InvoiceDetails updatedInvoiceDetails = invoiceDetailsMapper.updateToEntity(request, theInvoiceDetails);
        InvoiceDetails saved = invoicesDetailsRepository.save(updatedInvoiceDetails);
        return invoiceDetailsMapper.entityToDTO(saved);
    }

    public void deleteInvoiceDetail(InvoiceDetails invoiceDetails){
        invoicesDetailsRepository.delete(invoiceDetails);
    }

}
