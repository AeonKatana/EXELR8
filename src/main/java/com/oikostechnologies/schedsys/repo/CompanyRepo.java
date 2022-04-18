package com.oikostechnologies.schedsys.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oikostechnologies.schedsys.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

	public Page<Company> findByCompnameContaining(String search ,Pageable page);
	
	Company findByCompname(String name);
	
}
