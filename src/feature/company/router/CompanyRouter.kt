package feature.company.router

import com.cabratech.canopus.api.feature.company.controller.CompanyController
import database.company.Company
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.koin.ktor.ext.inject

fun Routing.companyRoot() {

	val companyController: CompanyController by inject()

	get("company-management/company/id") {
		val company = call.receive<Company>()
		companyController.submitSaveCompany(company)
	}

}
