package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.CompanyDna;

public interface CompanyDnaRepo extends JpaRepository<CompanyDna, Long> {

	
	CompanyDna findByCompany(Company company);
}
