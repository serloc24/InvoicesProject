package com.factures.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record InvoiceResponse(long id,
                              LocalDate date,
                              String state,
                              String reason,
                              LocalDate dueDate,
                              BigDecimal totalAmount,
                              CompanyResponse company,
                              ClientResponse client,
                              List<InvoiceDetailsResponse> lines) {
}
