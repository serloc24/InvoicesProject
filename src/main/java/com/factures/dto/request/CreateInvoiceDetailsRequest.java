package com.factures.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateInvoiceDetailsRequest(@NotBlank(message = "Invoice Id cannot be blank")
                                          long invoiceId,
                                          @NotBlank(message = "Product Id cannot be blank")
                                          long productId,
                                          @NotBlank(message = "Amount cannot be blank")
                                          int amount) {
}
