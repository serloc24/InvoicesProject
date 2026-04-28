package com.factures.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateInvoiceDetailsRequest(@NotBlank long invoiceId,
                                          @NotBlank long productId,
                                          @NotBlank int amount) {
}
