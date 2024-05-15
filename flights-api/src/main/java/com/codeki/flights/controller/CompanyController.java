package com.codeki.flights.controller;

import com.codeki.flights.dto.ResponseDto;
import com.codeki.flights.model.Company;
import com.codeki.flights.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Company>> getCompanyByName(@RequestParam String name) {
        return new ResponseEntity<>(companyService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.create(company), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return new ResponseEntity<>(companyService.update(id, company), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteCompany(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.deleteById(id), HttpStatus.OK);
    }
}
