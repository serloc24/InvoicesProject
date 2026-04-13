package com.factures.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest(@NotBlank(message = "Name cannot be blank")
                                  String name,
                                  @NotBlank(message = "Email cannot be blank")
                                  @Email(message = "The email must be valid")
                                  String email,
                                  @NotBlank(message = "Address cannot be blank")
                                  String address) {
}
