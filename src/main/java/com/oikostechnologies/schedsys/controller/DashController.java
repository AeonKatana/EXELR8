package com.oikostechnologies.schedsys.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.NotificationRepo;
import com.oikostechnologies.schedsys.repo.QuickViewRepo;
import com.oikostechnologies.schedsys.repo.RoleRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.CompanyService;
import com.oikostechnologies.schedsys.service.DailyTaskService;
import com.oikostechnologies.schedsys.service.SchedulerService;
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
	private DailyTaskService dailyservice;
	
	@Autowired
	private DepartmentRepo deptrepo;
	
	@Autowired
	private ActlogRepo actrepo;
	
	@Autowired
	private RoleRepo rolerepo;
	
	@Autowired
	private SchedulerService schedservice;
	
	@Autowired
	private NotificationRepo notifrepo;
	
	@RequestMapping("/")
	public String dashboard(Model model, @AuthenticationPrincipal MyUserDetails userdetail) {
		
		
		// I need Overdue for Masteradmin, and Others
		model.addAttribute("usercount", userservice.usercount());
		model.addAttribute("companycount", comservice.companycount());
		model.addAttribute("usercompcount", userservice.getAllByCompany(userdetail).size());
		//model.addAttribute("completed", detailrepo.countCompleted()); 
		model.addAttribute("dailies", dailyservice.countDailyToday()); // For Superadmin
		model.addAttribute("dailycomp", dailyservice.countCompanyDaily(userdetail.getUser().companyname())); // For Masteradmin
		model.addAttribute("mydaily", dailyservice.countMyDaily(userdetail.getUser().getId())); // For Other Roles
		model.addAttribute("overduecount", dailyservice.countOverdue()); // For Superadmin
		model.addAttribute("myoverdue", dailyservice.countOverdueByUser(userdetail.getUser().getId()));
		model.addAttribute("companyoverdue", dailyservice.countOverdueByCompany(userdetail.getUser().companyname()));
		model.addAttribute("assignedtome", dailyservice.countDailyAssignedToMeBySomeoneElse(userdetail.getUser().getId()));
		model.addAttribute("tardyuser", schedservice.getTardyusers());
		
		System.out.println(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		
		
		return "dashboard";
	}
	// --------------------------------------------- REST API
	
	@GetMapping("/dashboard/tardy")
	@ResponseBody
	public List<User> tardyUsers(){
		return schedservice.getTardyusers();
	}
	
	@GetMapping("/dashboard/overdue")
	@ResponseBody
	public List<DailyTask> overdueUsers(@AuthenticationPrincipal MyUserDetails detail){
		if(detail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			return dailyservice.getAllOverdue();
		}
		return dailyservice.getAllOverdueByUser(detail.getUser());
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
		
		Page<User> masteradmins = userservice.findAllUsers(0);
		model.addAttribute("parameter", null);
		model.addAttribute("currentPage" , 1);
		model.addAttribute("masteradmins", masteradmins);
		model.addAttribute("totalelement", masteradmins.getTotalElements());
		model.addAttribute("totalpage", masteradmins.getTotalPages());;
		model.addAttribute("comppersonnel" , userservice.getAllByCompany(user));
		
		System.out.println("-------------------------------");
		for(User u : userservice.findAllUsers()) {
			System.out.println(u.companyname());
		}
		
		return "personnel";
	}
	
	@GetMapping("/dashboard/companies")
	public String companylist( @AuthenticationPrincipal MyUserDetails user,Model model) {
		Page<Company> mastercomp = comservice.getCompanies();
		model.addAttribute("masteradmincomp", mastercomp);
		model.addAttribute("totalelement", mastercomp.getTotalElements());
		model.addAttribute("totalpage", mastercomp.getTotalPages());
		model.addAttribute("roles", rolerepo.findAll());
		model.addAttribute("company", comservice.getCompany(user.getUser().companyname()));
		return "companyprofile";
	}
	
//	@GetMapping("/dashboard/task/mytask")
//	public String task(Model model, @AuthenticationPrincipal MyUserDetails userdetails) {
//		model.addAttribute("currentUser", userdetails.getUser().getId());
//		model.addAttribute("mytask", dailyservice.getAllTask());
//		model.addAttribute("currentDate", ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
//		return "task";
//	}
	
//	@GetMapping("/dashboard/task/assignedtask")
//	public String assignedTask(Model model) {
//		return "pnltask";
//	}
		
	@GetMapping("/dashboard/activitylog")
	public String activity(Model model) {
		
		model.addAttribute("activity", actrepo.findAllByOrderByDateDesc());
		
		return "activitylog";
	}
	
	@GetMapping("/dashboard/notification")
	public String notifications(@AuthenticationPrincipal MyUserDetails user,Model model) {
		model.addAttribute("notifications", notifrepo.findAllByUser(user.getUser(), PageRequest.of(0, 10 , Sort.by("Id").descending())));
		return "notifications";
	}
	
	
	
}
