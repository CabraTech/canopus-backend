package com.br.cabratech.canopus.canopusbackend.controller;

import com.br.cabratech.canopus.canopusbackend.model.Company;
import com.br.cabratech.canopus.canopusbackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/canopus")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping("/company-management/company")
	@ResponseStatus(HttpStatus.CREATED)
	public Company create(@RequestBody Company company) {
		return companyService.create(company);
	}

	@PutMapping("/company-management/company")
	public Company update(@RequestBody Company company) {
		return companyService.create(company);
	}

	@DeleteMapping("/company-management/company/{id}")
	public void delete(@PathVariable String id) {
		companyService.delete(id);
	}

	@GetMapping("/company-management/company/{id}")
	public Company retrieve(@PathVariable String id) {
		return companyService.findById(id);
	}

	@GetMapping("/company-management/companies")
	public List<Company> getAll() {
		return companyService.findAll();
	}
}
