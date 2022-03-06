package com.oikostechnologies.schedsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.repo.CompanyRepo;

@Service
public class CompanyServiceImp implements CompanyService {

	@Autowired
	private CompanyRepo companyrepo;
	
	@Override
	public long companycount() {
		return companyrepo.count();
	}

	@Override
	public List<Company> getCompanies() {
		
		return companyrepo.findAll();
	}

}
