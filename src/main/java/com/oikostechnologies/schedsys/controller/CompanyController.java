package com.oikostechnologies.schedsys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.CompanyDna;
import com.oikostechnologies.schedsys.model.CompanyModel;
import com.oikostechnologies.schedsys.model.CoreValueModel;
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
	private String viewCompany(@PathVariable("company") String company, Model model,@AuthenticationPrincipal MyUserDetails user ,HttpServletRequest request) {
		HttpSession session = request.getSession(true); // For DataTable Purposes hehe
		session.setAttribute("companyview", company);
		Company viewcomp = companyService.getCompany(company);
		if(viewcomp == null) {
			model.addAttribute("company", null);
			return "redirect:/dashboard/companies";
		}else {
			if(user.getUser().getCompany() == null) {
				model.addAttribute("company", viewcomp);
				model.addAttribute("comppersonnel" , userservice.getAllByCompany(viewcomp.getCompname()));
				return "viewcompany";
			}
			else if(user.getUser().getCompany().getId() == viewcomp.getId()) {
				return "redirect:/dashboard/companies";
			}
			
			model.addAttribute("company", viewcomp);
			model.addAttribute("comppersonnel" , userservice.getAllByCompany(viewcomp.getCompname()));
			return "viewcompany";
		}
		
	}
	
	
	@PostMapping("/addDNA")
	@ResponseBody
	public String addDna(@AuthenticationPrincipal MyUserDetails user,CompanyDna dna) {
		return companyService.addDna(dna, dna.getCompanyid());
	}
	
	@PostMapping("/addCoreValue")
	@ResponseBody
	public String addCoreValue(CoreValueModel model, @AuthenticationPrincipal MyUserDetails user){
		
		return companyService.addCoreValue(model, user.getUser());
		
	}
	
	
	@PostMapping("/addcompany")
	@ResponseBody
	public int addCompany(@AuthenticationPrincipal MyUserDetails detail,CompanyModel company, UserModel user, HttpServletRequest request) {
		return companyService.addCompany(detail,company, user , request);
		
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
	public List<Company> companyList(){
		return companyService.findAll();
	}
}
