package com.br.cabratech.canopus.canopusbackend.controller;

import com.br.cabratech.canopus.canopusbackend.model.Company;
import com.br.cabratech.canopus.canopusbackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/canopus")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = "/company-management/company", method = RequestMethod.POST)
	public Company create(@RequestBody Company company) {
		return companyService.create(company);
	}

	@RequestMapping(value = "/company-management/company", method = RequestMethod.PUT)
	public Company update(@RequestBody Company company) {
		return companyService.create(company);
	}

	@RequestMapping(value = "/company-management/company/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		companyService.delete(id);
	}

	@RequestMapping(value = "/company-management/company/{id}", method = RequestMethod.GET)
	public Company retrieve(@PathVariable String id) {
		return companyService.findById(id);
	}

	@RequestMapping(value = "/company-management/companies", method = RequestMethod.GET)
	public List<Company> getAll() {
		return companyService.findAll();
	}
}
