package com.factures.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateInvoiceRequest(@NotBlank(message = "Reason cannot be blank")
                                   String reason,
                                   @NotBlank(message = "Client id cannot be blank")
                                   long clientId,
                                   @NotBlank(message="Company ID cannot be empty")
                                   long companyId,
                                   @Valid
                                   List<CreateInvoiceDetailsRequest> lines
                                   ) {
}
