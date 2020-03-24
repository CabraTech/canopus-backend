package com.br.cabratech.canopus.canopusbackend.repository;

import com.br.cabratech.canopus.canopusbackend.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

	public Company findByCnpj(String cnpj);

}
