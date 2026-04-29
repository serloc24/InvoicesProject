package com.factures.Controllers;

import com.factures.Service.CompanyService;
import com.factures.dto.request.CreateCompanyRequest;
import com.factures.dto.request.UpdateCompanyRequest;
import com.factures.dto.response.CompanyResponse;
import com.factures.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }


    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping(path = "/search")
    public ResponseEntity<CompanyResponse> getCompany(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String email){

        return ResponseEntity.ok(companyService.getCompanyByNameOrEmail(name, email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CreateCompanyRequest companyRequest){
        CompanyResponse theCompany = companyService.createCompany(companyRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(theCompany.id())
                .toUri();

        return ResponseEntity.created(location).body(theCompany);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable long id, @RequestBody UpdateCompanyRequest request){
        return ResponseEntity.ok(companyService.updateCompany(request, id));
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id){
        companyService.deleteCompany(id);
    }
}
