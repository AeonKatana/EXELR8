package com.oikostechnologies.schedsys.service;

import java.util.List;

import com.oikostechnologies.schedsys.entity.Company;

public interface CompanyService {

	public long companycount();
	public List<Company> getCompanies();
	
}
