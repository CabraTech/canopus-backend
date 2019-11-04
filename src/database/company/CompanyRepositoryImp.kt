package database.company

import com.cabratech.canopus.api.database.MongoDataService

class CompanyRepositoryImp(private val database: MongoDataService) : CompanyRepository {

	override suspend fun save(company: Company) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}
