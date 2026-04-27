package com.factures.dto.request;

import java.math.BigDecimal;

public record UpdateProductRequest(String description, BigDecimal unitPrice, BigDecimal taxes) {
}
