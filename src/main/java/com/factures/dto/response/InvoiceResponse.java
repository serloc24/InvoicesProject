package com.factures.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record InvoiceResponse(long id,
                              LocalDate date,
                              String state,
                              String reason,
                              @JsonFormat(pattern = "yyyy-MM-dd")
                              LocalDate dueDate,
                              BigDecimal totalAmount,
                              CompanyResponse company,
                              ClientResponse client,
                              List<InvoiceDetailsResponse> lines) {
}
