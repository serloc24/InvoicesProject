package com.factures.Repository;

import com.factures.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
    /*
    Automagically implemented by JpaRepository
        companyRepository.findAll()           // SELECT * FROM companies
        companyRepository.findById(1L)        // SELECT * WHERE id = 1
        companyRepository.save(company)       // INSERT o UPDATE
        companyRepository.deleteById(1L)      // DELETE WHERE id = 1
        companyRepository.existsById(1L)      // existe?
        companyRepository.count()             // COUNT(*)
     */

    Optional<Client> findByEmail(String email);
    Optional<Client> findByName(String name);
}
