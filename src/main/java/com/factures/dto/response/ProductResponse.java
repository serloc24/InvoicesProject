package com.factures.dto.response;

import java.math.BigDecimal;

public record ProductResponse(String description, BigDecimal unitPrice, BigDecimal taxes) {
}
