package com.oikostechnologies.schedsys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.model.CompanyModel;
import com.oikostechnologies.schedsys.model.UserModel;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.CompanyService;
import com.oikostechnologies.schedsys.service.UserService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userservice;
	
	
	
	@GetMapping("/{company}")
	private String viewCompany(@PathVariable("company") String company, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true); // For DataTable Purposes hehe
		session.setAttribute("companyview", company);
		Company viewcomp = companyService.getCompany(company);
		if(viewcomp == null) {
			model.addAttribute("company", null);
			return "viewcompany";
		}
		model.addAttribute("company", viewcomp);
		model.addAttribute("comppersonnel" , userservice.getAllByCompany(viewcomp.getCompname()));
		return "viewcompany";
	}
	
	
	@PostMapping("/addcompany")
	@ResponseBody
	public boolean addCompany(@AuthenticationPrincipal MyUserDetails detail,CompanyModel company, UserModel user, HttpServletRequest request) {
		companyService.addCompany(detail,company, user , request);
		
		return true;
	}
	
	
	
/**	
 * Manual Search and pagination with spring
 * 
	@GetMapping("/page/{page}") 
	public String companylist(Model model, @PathVariable("page") int page) {
		model.addAttribute("masteradmincomp", companyService.getCompanies());
		model.addAttribute("totalelement", companyService.getCompanies().getTotalElements());
		model.addAttribute("totalpage", companyService.getCompanies().getTotalPages());
		return "companyprofile";
	}
	
	@GetMapping("/search")
	public String companylist(Model model, @RequestParam("search") String search) {
		model.addAttribute("masteradmincomp", companyService.searchCompany(search));
		model.addAttribute("totalelement", companyService.searchCompany(search).getTotalElements());
		model.addAttribute("totalpage", companyService.searchCompany(search).getTotalPages());
		return "companyprofile";
	}
**/
	@GetMapping("/datatable") // End point for DataTable Jquery Ajax
	@ResponseBody
	public DataTablesOutput<Company> companyList(@Valid DataTablesInput input , @RequestParam Map<String, String> queryParam){
		return companyService.findAll(input);
	}
}
