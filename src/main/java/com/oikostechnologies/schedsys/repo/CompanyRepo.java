package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Long> {

}
