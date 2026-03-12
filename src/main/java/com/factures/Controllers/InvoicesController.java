package com.factures.Controllers;

import com.factures.Service.ClientsService;
import com.factures.Service.InvoiceDetailsService;
import com.factures.Service.InvoiceService;
import com.factures.entities.Client;
import com.factures.entities.InvoiceDetails;
import com.factures.entities.Invoice;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoice(){
        List<Invoice> allInvoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(allInvoices);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Invoice>> getInvoiceByClient(@PathVariable Long clientId){
        List<Invoice> allInvoices = invoiceService.getAllInvoicesByClient(clientId);
        return ResponseEntity.ok(allInvoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id){
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Invoice>> getInvoiceByCompany(@PathVariable Long companyId){
        return  ResponseEntity.ok(invoiceService.getInvoicesByCompany(companyId));
    }

    @GetMapping(params = "state")
    public ResponseEntity<List<Invoice>> getInvoiceByState(@RequestParam String state){
        return ResponseEntity.ok(invoiceService.getInvoicesByState(state));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice){
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        //Creating the location to the response
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{itemId}")
                .buildAndExpand(createdInvoice.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdInvoice);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody Invoice updatedInvoice, @PathVariable Long invoiceId){
        invoiceService.updateInvoice(updatedInvoice, invoiceId);
    }

    @PatchMapping(value = "/{invoiceId}", params = "state")
    public ResponseEntity<Invoice> updateState(@RequestParam Long invoiceId, @RequestBody String newState){
        Invoice updatedInvoice = invoiceService.updateState(invoiceId,newState);
        return ResponseEntity.ok(updatedInvoice);
    }


}
