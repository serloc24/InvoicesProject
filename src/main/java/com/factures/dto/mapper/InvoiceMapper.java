package com.factures.dto.mapper;

import com.factures.dto.request.CreateInvoiceRequest;
import com.factures.dto.request.PatchInvoiceRequest;
import com.factures.dto.request.UpdateInvoiceRequest;
import com.factures.dto.response.InvoiceResponse;
import com.factures.entities.Invoice;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {
    private final CompanyMapper companyMapper;
    private final ClientMapper clientMapper;
    private final InvoiceDetailsMapper invoiceDetailsMapper;

    // Constructor Injection
    public InvoiceMapper(CompanyMapper companyMapper, ClientMapper clientMapper, InvoiceDetailsMapper invoiceDetailsMapper) {
        this.companyMapper = companyMapper;
        this.clientMapper = clientMapper;
        this.invoiceDetailsMapper = invoiceDetailsMapper;
    }

    public Invoice updateToEntity(UpdateInvoiceRequest request, Invoice existingInvoice){
        existingInvoice.setState(request.state());
        existingInvoice.setReason(request.reason());
        existingInvoice.setDueDate(request.dueDate());
        existingInvoice.setTotalAmount(request.totalAmount());
        return existingInvoice;
    }

    public Invoice createToEntity(CreateInvoiceRequest request){
       Invoice theInvoice = new Invoice();
       theInvoice.setReason(request.reason());
       return theInvoice;
    }

    public InvoiceResponse entityToDTO(Invoice invoice){
            return new InvoiceResponse(
                    invoice.getId(),
                    invoice.getDate(),
                    invoice.getState(),
                    invoice.getReason(),
                    invoice.getDueDate(),
                    invoice.getTotalAmount(),
                    invoice.getCompany().getId(),
                    invoice.getClient().getId()

            );
    }

    public List<InvoiceResponse> entitiesToDTOList(List<Invoice> invoices) {
        return invoices.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    public Invoice partialUpdateEntityFromRequest(PatchInvoiceRequest request, Invoice existingInvoice){
        request.state().ifPresent(existingInvoice::setState);
        request.reason().ifPresent(existingInvoice::setReason);
        request.dueDate().ifPresent(existingInvoice::setDueDate);
        return existingInvoice;
    }
}
