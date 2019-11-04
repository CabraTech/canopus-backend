package com.cabratech.canopus.api.di

import com.cabratech.canopus.api.database.MongoDataService
import com.cabratech.canopus.api.database.MongoDataService.Companion.DATABASE_NAME
import com.cabratech.canopus.api.feature.company.controller.CompanyController
import com.cabratech.canopus.api.feature.company.controller.CompanyControllerImp
import com.mongodb.MongoClient
import database.company.CompanyRepository
import database.company.CompanyRepositoryImp
import org.koin.dsl.module

object AppModuleBuilder {

	fun modules() = listOf(companyModule)

	private var companyModule = module {

		//Repository
		single<CompanyRepository> { CompanyRepositoryImp(get()) }

		//database
		single { MongoDataService(MongoClient(), DATABASE_NAME) }

		//presenter
		single<CompanyController> { CompanyControllerImp(get()) }
	}

}
