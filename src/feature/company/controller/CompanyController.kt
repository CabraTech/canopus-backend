package com.cabratech.canopus.api.feature.company.controller

import database.company.Company

interface CompanyController {

	suspend fun submitSaveCompany(company : Company)
}
