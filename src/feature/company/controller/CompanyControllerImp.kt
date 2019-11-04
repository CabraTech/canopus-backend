package com.cabratech.canopus.api.feature.company.controller

import database.company.Company
import database.company.CompanyRepository

class CompanyControllerImp(private val companyRepository: CompanyRepository) : CompanyController {

	override suspend fun submitSaveCompany(company: Company) = companyRepository.save(company)

}
