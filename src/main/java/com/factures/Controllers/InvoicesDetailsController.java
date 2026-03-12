package com.factures.Controllers;

import com.factures.Service.InvoiceDetailsService;
import com.factures.Service.InvoiceService;
import com.factures.entities.InvoiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice-details")
public class InvoicesDetailsController {
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetails> getInvoiceDetailbyId(@RequestParam Long id){
            InvoiceDetails theInvoiceDetail = invoiceDetailsService.getInvoiceDetailById(id);
            return ResponseEntity.ok(theInvoiceDetail);
    }

    @PutMapping("/{invoiceDetailsId}")
    public ResponseEntity<InvoiceDetails> updateInvoiceDetailById(@RequestParam Long id, @RequestBody InvoiceDetails updatedInvoiceDetail){
        return ResponseEntity.ok(invoiceDetailsService.updateInvoiceDetail(id, updatedInvoiceDetail));

    }

}
