package com.codeki.flights.service;

import com.codeki.flights.dto.ResponseDto;
import com.codeki.flights.exceptions.NotFoundException;
import com.codeki.flights.model.Company;
import com.codeki.flights.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        }
        throw new NotFoundException("Company not found");
    }

    public List<Company> findByName(String name) {
        List<Company> companyList = companyRepository.findByNameContainingIgnoreCase(name);
        if (!companyList.isEmpty()) {
            return companyList;
        }
        throw new NotFoundException("No results found");
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company update(Long id, Company company) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            company.setId(id);
            return companyRepository.save(company);
        }
        throw new NotFoundException("Unable to update: Company not found");
    }

    public ResponseDto deleteById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            companyRepository.deleteById(id);
            return new ResponseDto("The company " + id + " has been deleted");
        }
        throw new NotFoundException("Unable to delete: Company not found");
    }
}
