package com.oikostechnologies.schedsys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.CompanyDna;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.CompanyModel;
import com.oikostechnologies.schedsys.model.CoreValueModel;
import com.oikostechnologies.schedsys.model.UserModel;
import com.oikostechnologies.schedsys.security.MyUserDetails;

public interface CompanyService {

	public long companycount();
	public Page<Company> getCompanies();
	public int addCompany(@AuthenticationPrincipal MyUserDetails detail,CompanyModel company, UserModel user, HttpServletRequest request);
	public Page<Company> searchCompany(String search);
	public DataTablesOutput<Company> findAll(DataTablesInput input);
	public DataTablesOutput<Company> findAll(DataTablesInput input ,Specification<Company> spec);
	public Company getCompany(String name);
	public String addDna(CompanyDna dna, long id);
	public List<Company> findAll();
	public String addCoreValue(CoreValueModel model, User user);
}
