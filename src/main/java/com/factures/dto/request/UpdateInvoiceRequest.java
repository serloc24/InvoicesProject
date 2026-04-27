package com.factures.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateInvoiceRequest(@NotNull(message = "ID cannot be null")
                                       long id,

                                   @NotBlank(message = "State cannot be blank")
                                       String state,

                                   @NotBlank(message = "Reason cannot be blank")
                                       String reason,

                                   LocalDate dueDate,

                                   BigDecimal totalAmount) {
}
