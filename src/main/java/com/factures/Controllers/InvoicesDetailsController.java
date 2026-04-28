package com.factures.Controllers;

import com.factures.Service.InvoiceDetailsService;
import com.factures.Service.InvoiceService;
import com.factures.dto.mapper.InvoiceDetailsMapper;
import com.factures.dto.response.InvoiceDetailsResponse;
import com.factures.entities.InvoiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice-details")
public class InvoicesDetailsController {

    private final InvoiceDetailsMapper invoiceDetailsMapper;
    private final InvoiceDetailsService invoiceDetailsService;
    private final InvoiceService invoiceService;

    public InvoicesDetailsController(InvoiceDetailsMapper invoiceDetailsMapper, InvoiceDetailsService invoiceDetailsService, InvoiceService invoiceService) {
        this.invoiceDetailsMapper = invoiceDetailsMapper;
        this.invoiceDetailsService = invoiceDetailsService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailsResponse> getInvoiceDetailbyId(@PathVariable Long id){
            InvoiceDetailsResponse theInvoiceDetail = invoiceDetailsService.getInvoiceDetailById(id);
            return ResponseEntity.ok(theInvoiceDetail);
    }

    @PutMapping("/{invoiceDetailsId}")
    public ResponseEntity<InvoiceDetailsResponse> updateInvoiceDetailById(@PathVariable Long id,
                                                                          @RequestBody InvoiceDetails updatedInvoiceDetail){
        return ResponseEntity.ok(invoiceDetailsService.updateInvoiceDetail(id, updatedInvoiceDetail));

    }

}
