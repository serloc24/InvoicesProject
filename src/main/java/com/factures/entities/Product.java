package com.factures.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products", schema = "facturam_db")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private BigDecimal taxes;


    public Product() {
    }

    public Product(String description, BigDecimal unitPrice, BigDecimal taxes) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.taxes = taxes;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }
}
