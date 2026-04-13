package com.factures.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CreateInvoiceRequest(@NotBlank(message = "Reason cannot be blank")
                                   String reason,
                                   @NotBlank(message = "Client id cannot be blank")
                                   long clientId,
                                   @Valid
                                   List<CreateInvoiceDetailsRequest> lines
                                   ) {
}
