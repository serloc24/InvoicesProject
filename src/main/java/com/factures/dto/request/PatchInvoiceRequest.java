package com.factures.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public record PatchInvoiceRequest(Optional<String> state,
                                  Optional<String> reason,
                                  Optional<LocalDate> dueDate
                                  ){

    public static PatchInvoiceRequest of(String state, String reason,LocalDate dueDate){
        return new PatchInvoiceRequest(
                Optional.ofNullable(state),
                Optional.ofNullable(reason),
                Optional.ofNullable(dueDate)
                );
    }
}
