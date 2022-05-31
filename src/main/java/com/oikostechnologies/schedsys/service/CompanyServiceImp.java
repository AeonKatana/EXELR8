package com.oikostechnologies.schedsys.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.datatable.repo.CompanyDataTable;
import com.oikostechnologies.schedsys.entity.ActivityLog;
import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.CompanyDna;
import com.oikostechnologies.schedsys.entity.CoreValue;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserRole;
import com.oikostechnologies.schedsys.event.CompanyEvent;
import com.oikostechnologies.schedsys.model.CompanyModel;
import com.oikostechnologies.schedsys.model.CoreValueModel;
import com.oikostechnologies.schedsys.model.UserModel;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.CompanyDnaRepo;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.CoreValueRepo;
import com.oikostechnologies.schedsys.repo.RoleRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.repo.UserRoleRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;

@Service
public class CompanyServiceImp implements CompanyService {

	@Autowired
	private CompanyRepo companyrepo;

	@Autowired
	private UserRepo userrepo;

	@Autowired
	private UserRoleRepo userrolerepo;

	@Autowired
	private RoleRepo rolerepo;

	@Autowired
	private CompanyDataTable companytable;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ActlogRepo actrepo;
	
	@Autowired
	private CompanyDnaRepo dnarepo;

	@Autowired
	private CoreValueRepo corerepo;
	
	@Override
	public long companycount() {
		return companyrepo.count();
	}

	@Override
	public Page<Company> getCompanies() {
		return companyrepo.findAll(PageRequest.of(0, 5));
	}

	@Override
	public int addCompany(@AuthenticationPrincipal MyUserDetails detail, CompanyModel company, UserModel user,
			HttpServletRequest request) {
		User useremail = userrepo.findByEmail(user.getUseremail());
		Company companyname = companyrepo.findByCompname(company.getCompanyname());
		if(useremail == null && companyname == null) {
			Company comp = Company.builder().color(company.getCompanycolor()).compname(company.getCompanyname())
					.build();
			User master = User.builder().firstname(user.getFname()).lastname(user.getLname())
					.contactno(user.getContact()).email(user.getUseremail()).company(comp).build();

			UserRole ur = UserRole.builder().role(rolerepo.findById(2L).orElse(null)).user(master).build();

			companyrepo.save(comp);
			
			CompanyDna dna = new CompanyDna();
			dna.setCompany(comp);
			dnarepo.save(dna);
			
			userrepo.save(master);

			
			
			
			userrolerepo.save(ur);
			publisher.publishEvent(new CompanyEvent(detail, master, applicationUrl(request)));

			ActivityLog compcreate = new ActivityLog();
			compcreate.setAction("has created a company");
			compcreate.setTarget(comp.getCompname());
			compcreate.setTargetlink("#");
			compcreate.setUser(detail.getUser());
			compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());

			ActivityLog createuser = new ActivityLog();
			createuser.setAction("has created a user");
			createuser.setTarget(user.getFname() + " " + user.getLname());
			createuser.setTargetlink("#");
			createuser.setUser(master);
			createuser.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());

			actrepo.save(compcreate);
			actrepo.save(createuser);
			return 1;
		}
		else if(companyname != null){
			return 2;
		}
		else if(useremail != null) {
			return 3;
		}
		return 4;
	}

	private String applicationUrl(HttpServletRequest request) {
		return "https://" + request.getServerName() + request.getContextPath();
	}

	@Override
	public Page<Company> searchCompany(String search) {
		return companyrepo.findByCompnameContaining(search, PageRequest.of(0, 5));
	}

	@Override
	public DataTablesOutput<Company> findAll(DataTablesInput input) {
		return companytable.findAll(input);
	}

	@Override
	public DataTablesOutput<Company> findAll(DataTablesInput input, Specification<Company> spec) {
		return companytable.findAll(input, spec);
	}

	@Override
	public Company getCompany(String name) {
		// TODO Auto-generated method stub
		return companyrepo.findByCompname(name);
	}

	@Override
	public String addDna(CompanyDna dna, long id) {
		Company company = companyrepo.findById(id).orElse(null);
		CompanyDna newdna = dnarepo.findByCompany(company);
		
		if(newdna == null) {
			dna.setCompany(company);
			dnarepo.save(dna);
		}
		else {
			newdna.setHistory(dna.getHistory());
			dnarepo.save(newdna);
		}
		
		return "DNA Successfully Updated!";
	}

	@Override
	public List<Company> findAll() {
		// TODO Auto-generated method stub
		return companyrepo.findAll();
	}

	@Override
	public String addCoreValue(CoreValueModel model, User user) {
		
		CoreValue value = corerepo.findById(model.getId()).orElse(null);
		Company company = companyrepo.findById(model.getCompanyid()).orElse(null);
		if(company == null) {
			return "An error occured.Company does not exist";
		}
		if(value == null) {
			value = new CoreValue();
			value.setName(model.getTitle());
			value.setDescription(model.getDescription());
			value.setDna(company.getDna());
			value.setIndicator(model.getIndicator());
			corerepo.save(value);
			return "Core Value Successfully Added!";
		}
		value.setName(model.getTitle());
		value.setDna(company.getDna());
		value.setDescription(model.getDescription());
		value.setIndicator(model.getIndicator());
		corerepo.save(value);
		return "Core Value Updated";
	}

}
