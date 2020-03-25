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


	public Company create(Company company) {
		return companyRepository.save(company);
	}

	public void delete(String id) {
		companyRepository.deleteById(id);
	}

	public Company findById(String companyId) {
		return companyRepository.findById(companyId).get();
	}

	public Company findByCnpj(String cnpj) {
		return companyRepository.findByCnpj(cnpj);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}


}
