package database.company

interface CompanyRepository {


	suspend fun  save(company: Company)

}
