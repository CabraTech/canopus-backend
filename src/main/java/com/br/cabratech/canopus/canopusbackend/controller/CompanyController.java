package com.br.cabratech.canopus.canopusbackend.controller;

import com.br.cabratech.canopus.canopusbackend.model.Company;
import com.br.cabratech.canopus.canopusbackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@RequestMapping("company")
	public String create(@RequestParam String name,
						 @RequestParam String cnpj,
						 @RequestParam String phone,
						 @RequestParam String email) {

		Company company = companyService.create(name, cnpj, phone, email);
		return company.toString();
	}

	@RequestMapping("companies")
	public List<Company> getAll() {
		return companyService.findAll();
	}
}
