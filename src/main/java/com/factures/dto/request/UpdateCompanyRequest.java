package com.factures.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record UpdateCompanyRequest(@NotNull long id,
                                   @NotBlank(message = "The company name cannot be blank")
                                   String name,
                                   Optional<String> email,
                                   Optional<String> address,
                                   Optional<String> iban
                                   ) {
}
