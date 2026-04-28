package com.factures.Controllers;

import com.factures.Service.ClientsService;
import com.factures.Service.InvoiceDetailsService;
import com.factures.Service.InvoiceService;
import com.factures.dto.request.CreateInvoiceRequest;
import com.factures.dto.request.PatchInvoiceRequest;
import com.factures.dto.request.UpdateInvoiceRequest;
import com.factures.dto.response.InvoiceResponse;
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

    private InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoice(){
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<InvoiceResponse>> getInvoiceByClient(@PathVariable Long clientId){
        return ResponseEntity.ok(invoiceService.getAllInvoicesByClient(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id){
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<InvoiceResponse>> getInvoiceByCompany(@PathVariable Long companyId){
        return  ResponseEntity.ok(invoiceService.getInvoicesByCompany(companyId));
    }

    @GetMapping(params = "state")
    public ResponseEntity<List<InvoiceResponse>> getInvoiceByState(@RequestParam String state){
        return ResponseEntity.ok(invoiceService.getInvoicesByState(state));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody CreateInvoiceRequest invoice){
        InvoiceResponse createdInvoice = invoiceService.createInvoice(invoice);
        //Creating the location to the response
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{itemId}")
                .buildAndExpand(createdInvoice.id())
                .toUri();
        return ResponseEntity.created(location).body(createdInvoice);
    }

    @PatchMapping(value = "/{invoiceId}")
    public ResponseEntity<InvoiceResponse> partialUpdate(@PathVariable Long invoiceId, @RequestBody PatchInvoiceRequest request){
        InvoiceResponse updatedInvoice = invoiceService.partialUpdateInvoice(invoiceId,request);
        return ResponseEntity.ok(updatedInvoice);
    }

}
