package com.oikostechnologies.schedsys.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.view.Quickview;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.QuickViewRepo;
import com.oikostechnologies.schedsys.repo.TaskDetailRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.CompanyService;
import com.oikostechnologies.schedsys.service.DailyTaskService;
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
	
	@Autowired
	private DailyTaskService dailyservice;
	
	@Autowired
	private DepartmentRepo deptrepo;
	
	@Autowired
	private UserRepo userepo;
	
	@Autowired
	private ActlogRepo actrepo;
	
	
	
	@RequestMapping("/")
	public String dashboard(Model model, @AuthenticationPrincipal MyUserDetails userdetail) {
		
		
		model.addAttribute("usercount", userservice.usercount());
		model.addAttribute("companycount", comservice.companycount());
		model.addAttribute("view", qrepo.findAll().size());
		model.addAttribute("compqview", qrepo.findAllByCompname(userdetail.getUser().companyname()).size());
		model.addAttribute("completed", detailrepo.countCompleted());
		model.addAttribute("dailies", dailyservice.countDailyToday());
		model.addAttribute("dailycomp", dailyservice.countCompanyDaily(userdetail.getUser().companyname()));
		model.addAttribute("mydaily", dailyservice.countMyDaily(userdetail.getUser().getId()));
		model.addAttribute("overduecount", dailyservice.countOverdue());
		
		System.out.println(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		
		return "dashboard";
	}
	// --------------------------------------------- REST API for Dashboard DataTable
	@GetMapping("/superview")
	@ResponseBody
	public List<Quickview> superview() {
		return qrepo.findAll();
	}
	
	@GetMapping("/compqview")
	@ResponseBody
	public List<Quickview> compqview(@AuthenticationPrincipal MyUserDetails userdetail){
		return qrepo.findAllByCompname(userdetail.getUser().companyname());
	}
	// ----------------------------------------------
	
	
	@GetMapping("/dashboard/department")
	public String departments(@AuthenticationPrincipal MyUserDetails user,Model model) {
		
		model.addAttribute("department", deptrepo.findAll());
		model.addAttribute("mydept", deptrepo.getAllByUser(user.getUser()));
		return "department";
	}
	
	
	@GetMapping("/dashboard/personnel")
	public String personel(Model model , @AuthenticationPrincipal MyUserDetails user) {
		model.addAttribute("parameter", null);
		model.addAttribute("currentPage" , 1);
		model.addAttribute("masteradmins", userservice.findAllUsers(0));
		model.addAttribute("totalelement", userservice.findAllUsers(0).getTotalElements());
		model.addAttribute("totalpage", userservice.findAllUsers(0).getTotalPages());;
		model.addAttribute("comppersonnel" , userservice.getAllByCompany(user));
		
		System.out.println("-------------------------------");
		for(User u : userservice.findAllUsers()) {
			System.out.println(u.companyname());
		}
		
		return "personnel";
	}
	
	@GetMapping("/dashboard/companies")
	public String companylist(Model model) {
		model.addAttribute("masteradmincomp", comservice.getCompanies());
		model.addAttribute("totalelement", comservice.getCompanies().getTotalElements());
		model.addAttribute("totalpage", comservice.getCompanies().getTotalPages());
		return "companyprofile";
	}
	
	@GetMapping("/dashboard/task/mytask")
	public String task(Model model, @AuthenticationPrincipal MyUserDetails userdetails) {
		model.addAttribute("currentUser", userdetails.getUser().getId());
		model.addAttribute("mytask", dailyservice.getAllTask());
		model.addAttribute("currentDate", ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
		return "task";
	}
	
	@GetMapping("/dashboard/task/assignedtask")
	public String assignedTask(Model model) {
		return "pnltask";
	}
		
	@GetMapping("/dashboard/activitylog")
	public String activity(Model model) {
		
		model.addAttribute("activity", actrepo.findAllByOrderByDateDesc());
		
		return "activitylog";
	}
	
	
	
	
}
