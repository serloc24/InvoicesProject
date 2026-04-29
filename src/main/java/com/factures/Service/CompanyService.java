package com.factures.Service;


import com.factures.Repository.CompaniesRepository;
import com.factures.dto.mapper.CompanyMapper;
import com.factures.dto.request.CreateCompanyRequest;
import com.factures.dto.request.UpdateCompanyRequest;
import com.factures.dto.response.CompanyResponse;
import com.factures.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompaniesRepository companiesRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompaniesRepository repository, CompanyMapper companyMapper){
        this.companiesRepository = repository;
        this.companyMapper = companyMapper;
    }

    @Transactional
    public CompanyResponse createCompany(CreateCompanyRequest request){
        if(companiesRepository.findByEmail(request.email()).isPresent()){
            throw new IllegalArgumentException("El email ya existe");
        }
        Company theCompany = companyMapper.createToEntity(request);
        Company saved = companiesRepository.save(theCompany);
        return companyMapper.entityToDTO(saved);
    }

    public List<CompanyResponse> getAllCompanies(){
        return  companyMapper.entitiesToDTOList(companiesRepository.findAll());
    }

    public CompanyResponse getCompanyByNameOrEmail(String name, String email){
        Company theCompany = new Company();
        if(name != null && email != null){
            theCompany = companiesRepository.findByNameAndEmail(name, email)
                                            .orElseThrow(() -> new RuntimeException("It does not exist Company with that name and email"));
        } else if (name != null) {
            theCompany = companiesRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("It does not exist a company with that name"));
        } else if (email != null) {
            theCompany = companiesRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("It does not exist a company with that email"));
        }
        return companyMapper.entityToDTO(theCompany);
    }

    public CompanyResponse getCompanyById(Long id){
        Company theCompany = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This Company ID doesn't exist"));
        return companyMapper.entityToDTO(theCompany);
    }

    @Transactional
    public CompanyResponse updateCompany(UpdateCompanyRequest request, long id){
        if (!companiesRepository.existsById(request.id()) && request.id() == id){
            throw new RuntimeException("This company ID does not exist");
        }
        Company theCompany = companiesRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Company Id does not exist"));
        companyMapper.updateToEntity(request, theCompany);
        Company updatedCompany = companiesRepository.save(theCompany);
        return companyMapper.entityToDTO(updatedCompany);

    }

    @Transactional
    public void deleteCompany(long id){
        Company theCompany = companiesRepository.findById(id)
                            .orElseThrow(()-> new IllegalArgumentException("Company Id does not exist"));
        companiesRepository.delete(theCompany);
    }
}
