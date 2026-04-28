package com.factures.Service;

import com.factures.Repository.ClientsRepository;
import com.factures.Repository.CompaniesRepository;
import com.factures.Repository.InvoicesRepository;
import com.factures.dto.mapper.ClientMapper;
import com.factures.dto.mapper.InvoiceMapper;
import com.factures.dto.request.CreateInvoiceDetailsRequest;
import com.factures.dto.request.CreateInvoiceRequest;
import com.factures.dto.request.PatchInvoiceRequest;
import com.factures.dto.request.UpdateInvoiceRequest;
import com.factures.dto.response.ClientResponse;
import com.factures.dto.response.InvoiceDetailsResponse;
import com.factures.dto.response.InvoiceResponse;
import com.factures.entities.Client;
import com.factures.entities.Company;
import com.factures.entities.Invoice;
import com.factures.entities.InvoiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoicesRepository invoicesRepository;
    private final ClientsRepository clientsRepository;
    private final CompaniesRepository companiesRepository;
    private final InvoiceDetailsService invoiceDetailsService;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoicesRepository invoicesRepository, ClientsRepository clientsRepository,
                          CompaniesRepository companiesRepository, InvoiceDetailsService invoiceDetailsService,
                          InvoiceMapper invoiceMapper, ClientMapper clientMapper) {
        this.invoicesRepository = invoicesRepository;
        this.clientsRepository = clientsRepository;
        this.companiesRepository = companiesRepository;
        this.invoiceDetailsService = invoiceDetailsService;
        this.invoiceMapper = invoiceMapper;
    }

    public List<InvoiceResponse> getAllInvoices() {
        return invoiceMapper.entitiesToDTOList(invoicesRepository.findAll());
    }
    public List<InvoiceResponse> getAllInvoicesByClient(Long clientId){
        Client theClient = clientsRepository.findById(clientId)
                            .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        List<Invoice> invoicesClient = invoicesRepository.findByClient(theClient);
        return invoiceMapper.entitiesToDTOList(invoicesClient);
    }
    public InvoiceResponse getInvoiceById(Long id){
        Invoice theInvoice = invoicesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        return  invoiceMapper.entityToDTO(theInvoice);
    }
    public List<InvoiceResponse> getInvoicesByCompany(Long companyId){

        Company theCompany = companiesRepository.findById(companyId)
                                                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        return invoiceMapper.entitiesToDTOList(invoicesRepository.findByCompany(theCompany));
    }
    public List<InvoiceResponse> getInvoicesByState(String state){
        return invoiceMapper.entitiesToDTOList(invoicesRepository.findByState(state));
    }


    public InvoiceResponse createInvoice(CreateInvoiceRequest request){
        Invoice theInvoice = invoiceMapper.createToEntity(request);
        //Retrieve all the company and client objects
        Client theClient = clientsRepository.findById(request.clientId())
                                            .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Company theCompany = companiesRepository.findById(request.companyId())
                                                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        theInvoice.setClient(theClient);
        theInvoice.setCompany(theCompany);
        Invoice savedInvoice = invoicesRepository.save(theInvoice);

        //Persist the Lines inside the invoice
        for(CreateInvoiceDetailsRequest detailRequest : request.lines()){
            invoiceDetailsService.createInvoiceDetail(detailRequest, savedInvoice);
        }

        return invoiceMapper.entityToDTO(savedInvoice);
    }

    public InvoiceResponse updateInvoice(UpdateInvoiceRequest updatedInvoiceRequest, Long id){
        Invoice theInvoice = invoicesRepository.findById(updatedInvoiceRequest.id())
                                                .orElseThrow(() -> new RuntimeException("Didn't find the invoice to update"));
        if(updatedInvoiceRequest.id() != id){
            throw new IllegalArgumentException("The id in the object is not the same you want to update");
        }
        Invoice updatedInvoice = invoiceMapper.updateToEntity(updatedInvoiceRequest,theInvoice);
        Invoice saved = invoicesRepository.save(updatedInvoice);
        return invoiceMapper.entityToDTO(saved);
    }

    public InvoiceResponse partialUpdateInvoice(Long id, PatchInvoiceRequest request){
        Invoice existingInvoice = invoicesRepository.findById(id)
                                    .orElseThrow(() -> new IllegalArgumentException("Invoice id not found"));
        Invoice updatedInvoice = invoiceMapper.partialUpdateEntityFromRequest(request, existingInvoice);
        Invoice saved = invoicesRepository.save(updatedInvoice);
        return invoiceMapper.entityToDTO(saved);
    }

    public void deleteInvoice(Long invoiceId){
        Invoice theInvoice =  invoicesRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("InvoiceId to delete not found"));
        invoicesRepository.delete(theInvoice);

    }


}
