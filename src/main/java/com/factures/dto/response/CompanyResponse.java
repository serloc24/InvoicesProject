package com.factures.dto.response;

import java.util.Objects;

public record CompanyResponse(String name, String email, String address, String iban) {

    public CompanyResponse{
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(address, "Address cannot be null");
    }
}
