package com.factures.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateClientRequest(@NotBlank(message = "To update ID is necessary")
                                  long id,
                                  String name, String email, String address) {
}
