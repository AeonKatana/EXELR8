package com.oikostechnologies.schedsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.view.Quickview;
import com.oikostechnologies.schedsys.repo.QuickViewRepo;
import com.oikostechnologies.schedsys.repo.TaskDetailRepo;
import com.oikostechnologies.schedsys.service.CompanyService;
import com.oikostechnologies.schedsys.service.UserService;

@Controller
@RequestMapping("/")
public class DashController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private CompanyService comservice;
	
	@Autowired
	private QuickViewRepo qrepo;
	
	@Autowired
	private TaskDetailRepo detailrepo;
	
	@GetMapping("/")
	public String dashboard(Model model) {
		
		model.addAttribute("usercount", userservice.usercount());
		model.addAttribute("companycount", comservice.companycount());
		model.addAttribute("view", qrepo.findAll());
		model.addAttribute("completed", detailrepo.countCompleted());
		
		return "dashboard";
	}
	
	@GetMapping("/dashboard/personnel")
	public String personel(Model model) {
		model.addAttribute("masteradmins", userservice.findAllUsers());
		return "personnel";
	}
	
	@GetMapping("/dashboard/companies")
	public String companylist(Model model) {
		
		model.addAttribute("masteradmincomp", userservice.getUsersByRole(1L));
		return "companyprofile";
	}
	
	@GetMapping("/dashboard/task/mytask")
	public String task(Model model) {
		return "task";
	}
	
	@GetMapping("/dashboard/task/assignedtask")
	public String assignedTask(Model model) {
		return "pnltask";
	}
		
	@GetMapping("/dashboard/activitylog")
	public String activity() {
		return "activitylog";
	}
	
	
	
}
