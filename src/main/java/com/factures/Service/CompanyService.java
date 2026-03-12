package com.factures.Service;


import com.factures.Repository.CompaniesRepository;
import com.factures.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompaniesRepository companiesRepository;

    public Company createCompany(Company company){
        if(companiesRepository.findByEmail(company.getEmail()).isPresent()){
            throw new IllegalArgumentException("El email ya existe");
        }
        return companiesRepository.save(company);
    }

    public List<Company> getAllCompanies(){
        return companiesRepository.findAll();
    }

    public Company getCompanyByName(String name){
        return companiesRepository.findByName(name).orElseThrow();
    }

    public Company getCompanyById(Long id){
        return companiesRepository.findById(id).orElseThrow();
    }
    public Company getCompanyByEmail(String email){
        return companiesRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("It does not exist a company with that email"));
    }

    public Company updateCompany(Company company){
        if (companiesRepository.existsById(company.getId())){
            return companiesRepository.save(company);
        }
        throw new RuntimeException("This company ID does not exist");
    }

}
