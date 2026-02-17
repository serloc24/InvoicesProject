package com.factures.entities;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Timestamp date;
    private String state;
    private String reason;

    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "tax_base")
    private long taxBase;

    @Column(name = "total_amount")
    private long totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_companies")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Clients client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetails> lines = new ArrayList<>();

    public Invoices() {
    }

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public long getTaxBase() {
        return taxBase;
    }

    public void setTaxBase(long taxBase) {
        this.taxBase = taxBase;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public List<InvoiceDetails> getLines() {
        return lines;
    }

    public void setLines(List<InvoiceDetails> lines) {
        this.lines = lines;
    }
}
