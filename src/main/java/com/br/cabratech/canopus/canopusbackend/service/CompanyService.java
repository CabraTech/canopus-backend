package com.br.cabratech.canopus.canopusbackend.service;


import com.br.cabratech.canopus.canopusbackend.model.Company;
import com.br.cabratech.canopus.canopusbackend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Company create(String name, String cnpj, String phone, String email) {
		return companyRepository.save(new Company(name, cnpj, phone, email));
	}

	public Company update(String name, String id) {
		Company company = companyRepository.findById(id).get();
		company.setName(name);
		companyRepository.save(company);
		return company;
	}

	public void delete(String id) {
		companyRepository.deleteById(id);
	}

	public Company findByCnpj(String cnpj) {
		return companyRepository.findByCnpj(cnpj);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}


}
