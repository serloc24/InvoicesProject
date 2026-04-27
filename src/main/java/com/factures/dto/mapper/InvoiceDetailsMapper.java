package com.factures.dto.mapper;

import com.factures.dto.request.CreateInvoiceDetailsRequest;
import com.factures.dto.response.InvoiceDetailsResponse;
import com.factures.entities.InvoiceDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceDetailsMapper {
    private final ProductMapper productMapper;

    // Constructor Injection
    public InvoiceDetailsMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public InvoiceDetails createToEntity(CreateInvoiceDetailsRequest request) {
        InvoiceDetails details = new InvoiceDetails();
        details.setAmount(request.amount());
        // Note: Invoice and Product will be resolved in the service layer
        return details;
    }
    public InvoiceDetailsResponse entityToDTO(InvoiceDetails details) {
        return new InvoiceDetailsResponse(
                productMapper.entityToDTO(details.getProduct()),
                details.getAmount()
        );
    }

    public List<InvoiceDetailsResponse> entitiesToDTOList(List<InvoiceDetails> detailsList) {
        return detailsList.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }



}
