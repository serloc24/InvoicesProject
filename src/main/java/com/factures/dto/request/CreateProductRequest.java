package com.factures.dto.request;

import java.math.BigDecimal;

public record CreateProductRequest(String description, BigDecimal unitPrice, BigDecimal taxes) {
}
