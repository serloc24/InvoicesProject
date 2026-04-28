package com.factures.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductResponse(@NotNull long id,
                              @NotBlank(message = "Must be a description to a Product")
                              String description,
                              @NotBlank(message = "Price cannot be blank")
                              BigDecimal unitPrice,
                              @NotBlank(message = "Taxes cannot be blank")
                              BigDecimal taxes) {
}
