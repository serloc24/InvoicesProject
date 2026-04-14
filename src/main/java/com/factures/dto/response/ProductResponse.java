package com.factures.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductResponse(String description,
                              @NotBlank(message = "Price cannot be blank")
                              BigDecimal unitPrice,
                              @NotBlank(message = "Taxes cannot be blank")
                              BigDecimal taxes) {
}
