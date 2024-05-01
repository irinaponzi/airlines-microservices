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

    // Busca y retorna todas las compañías
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    // Busca una compañía por su ID y la retorna. Si no la encuentra devuelve una excepción
    public Company findById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        }
        throw new NotFoundException("Company not found");
    }

    // Busca compañías que contengan en su nombre el String pasado por parámetro, ignorando mayúsculas y minúsculas
    // Retorna una lista con las compañías encontradas. Si la lista está vacía devuelve una excepción
    public List<Company> findByName(String name) {
        List<Company> companyList = companyRepository.findByNameContainingIgnoreCase(name);
        if (!companyList.isEmpty()) {
            return companyList;
        }
        throw new NotFoundException("No results found");
    }

    // Guarda una nueva compañía y la retorna
    public Company create(Company company) {
        return companyRepository.save(company);
    }

    // Actualiza los datos de una compañía existente, buscada por su ID, y la retorna
    // Si no la encuentra no realiza la actualización y devuelve una excepción
    public Company update(Long id, Company company) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            company.setId(id);
            return companyRepository.save(company);
        }
        throw new NotFoundException("Unable to update: Company not found");
    }

    // Elimina una compañía, buscada por su ID, y retorna un mensaje confirmando la acción
    // Si no la encuentra devuelve una excepción
    public ResponseDto deleteById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            companyRepository.deleteById(id);
            return new ResponseDto("The company " + id + " has been deleted");
        }
        throw new NotFoundException("Unable to delete: Company not found");
    }
}
