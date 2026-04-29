package com.factures.dto.mapper;

import com.factures.dto.request.CreateClientRequest;
import com.factures.dto.request.CreateCompanyRequest;
import com.factures.dto.request.UpdateClientRequest;
import com.factures.dto.request.UpdateCompanyRequest;
import com.factures.dto.response.ClientResponse;
import com.factures.dto.response.CompanyResponse;
import com.factures.entities.Client;
import com.factures.entities.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public Company createToEntity(CreateCompanyRequest request){
        return new Company(request.name(), request.email(), request.address());
    }

    public CompanyResponse entityToDTO(Company company){
        return new CompanyResponse(company.getName(), company.getEmail(), company.getAddress());
    }

    public List<CompanyResponse> entitiesToDTOList(List<Company> companies) {
        return companies.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public Company updateToEntity(UpdateCompanyRequest request, Company existingCompany){
        existingCompany.setName(request.name());
        request.email().ifPresent(existingCompany::setEmail);
        request.address().ifPresent(existingCompany::setAddress);
        request.iban().ifPresent(existingCompany::setIban);
        return existingCompany;
    }
}
