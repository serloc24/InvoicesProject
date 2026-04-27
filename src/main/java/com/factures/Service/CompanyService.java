package com.factures.Service;


import com.factures.Repository.CompaniesRepository;
import com.factures.dto.mapper.CompanyMapper;
import com.factures.dto.response.CompanyResponse;
import com.factures.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private CompaniesRepository companiesRepository;
    private CompanyMapper mapper;

    public CompanyService(CompaniesRepository repository, CompanyMapper mapper){
        this.companiesRepository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public Company createCompany(Company company){
        if(companiesRepository.findByEmail(company.getEmail()).isPresent()){
            throw new IllegalArgumentException("El email ya existe");
        }
        return companiesRepository.save(company);
    }

    public List<CompanyResponse> getAllCompanies(){
        return  mapper.entitiesToDTOList(companiesRepository.findAll());
    }

    public CompanyResponse getCompanyByNameOrEmail(String name, String email){
        Company theCompany = new Company();
        if(name != null && email != null){
            theCompany = companiesRepository.findByNameAndEmail(name, email)
                                            .orElseThrow(() -> new RuntimeException("It does not exist Company with that name and email"))
        } else if (name != null) {
            theCompany = companiesRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("It does not exist a company with that name"));
        } else if (email != null) {
            theCompany = companiesRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("It does not exist a company with that email"));
        }
        return mapper.entityToDTO(theCompany);
    }

    public CompanyResponse getCompanyById(Long id){
        Company theCompany = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This Company ID doesn't exist"));
        return mapper.entityToDTO(theCompany);
    }

    @Transactional
    public Company updateCompany(Company company){
        if (companiesRepository.existsById(company.getId())){
            return companiesRepository.save(company);
        }
        throw new RuntimeException("This company ID does not exist");
    }

}
