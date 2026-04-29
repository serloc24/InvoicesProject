package com.factures.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record CompanyResponse(@NotNull long id, @NotBlank String name, @NotBlank String email, String address) {

}
