package com.oikostechnologies.schedsys.controller;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.ActivityLog;
import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.Notification;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.NotificationRepo;
import com.oikostechnologies.schedsys.repo.RoleRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
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
	private UserRepo repo;
	
	@Autowired
	private NotificationRepo notifrepo;
	
	@RequestMapping("/")
	public String dashboard(Model model, @AuthenticationPrincipal MyUserDetails userdetail) {
		
		
		// I need Overdue for Masteradmin, and Others
		model.addAttribute("usercount", userservice.usercount());
		model.addAttribute("companycount", comservice.companycount());
		model.addAttribute("usercompcount", userservice.getAllByCompany(userdetail).size());
		
		model.addAttribute("dailies", dailyservice.countDailyToday()); // For Superadmin
		model.addAttribute("dailycomp", dailyservice.countCompanyDaily(userdetail.getUser().companyname())); // For Masteradmin
		model.addAttribute("mydaily", dailyservice.countMyDaily(userdetail.getUser().getId())); // For Other Roles
		model.addAttribute("overduecount", dailyservice.countOverdue()); // For Superadmin
		model.addAttribute("myoverdue", dailyservice.countOverdueByUser(userdetail.getUser().getId()));
		model.addAttribute("companyoverdue", dailyservice.countOverdueByCompany(userdetail.getUser().companyname()));
		model.addAttribute("assignedtome", dailyservice.countDailyAssignedToMeBySomeoneElse(userdetail.getUser().getId()));
		model.addAttribute("tardyuser", schedservice.getTardyUsers());
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(userdetail.getUser()));
		System.out.println(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		
		
		return "dashboard";
	}
	
	@GetMapping("/savepdf")
	public String pdffile() {
		return "savepdf";
	}
	// --------------------------------------------- REST API
	
	@GetMapping("/dashboard/tardy")
	@ResponseBody
	public List<User> tardyUsers(){
		return schedservice.getTardyUsers();

	}
	
	@GetMapping("/dashboard/overdue")
	@ResponseBody
	public List<DailyTask> overdueUsers(@AuthenticationPrincipal MyUserDetails detail){
		if(detail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			return dailyservice.getAllOverdue();
		}
		else if(detail.getUser().role().equalsIgnoreCase("MASTERADMIN")) {
			return dailyservice.getAllOverdueByCompany(detail.getUser().companyname());
		}
			return dailyservice.getAllOverdueByUser(detail.getUser());
	}
	
	@GetMapping("/dashboard/daily")
	@ResponseBody
	public List<DailyTask> dailyTask(@AuthenticationPrincipal MyUserDetails detail){
		 if(detail.getUser().role().equalsIgnoreCase("MASTERADMIN")) {
			return dailyservice.getAllDailyByCompany(detail.getUser().companyname());
		 }
			return dailyservice.getAllDailyByUser(detail.getUser());
	}

	// ----------------------------------------------
	
	
	@GetMapping("/dashboard/department")
	public String departments(@AuthenticationPrincipal MyUserDetails user,Model model) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(user.getUser()));
		model.addAttribute("department", deptrepo.findAll());
		model.addAttribute("mydept", deptrepo.getAllByUser(user.getUser()));
		model.addAttribute("companies", comservice.findAll());
		model.addAttribute("mycomp", comservice.getCompany(user.getUser().companyname()));
		return "department";
	}
	
	
	@GetMapping("/dashboard/personnel")
	public String personel(Model model , @AuthenticationPrincipal MyUserDetails user) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(user.getUser()));
		Page<User> masteradmins = userservice.findAllUsers(0);
		model.addAttribute("userrole", user.getUser().role());
		model.addAttribute("parameter", null);
		model.addAttribute("currentPage" , 1);
		model.addAttribute("masteradmins", masteradmins);
		model.addAttribute("totalelement", masteradmins.getTotalElements());
		model.addAttribute("totalpage", masteradmins.getTotalPages());;
		model.addAttribute("comppersonnel" , userservice.getAllByCompany(user));
		model.addAttribute("companies", comservice.findAll());
		System.out.println("-------------------------------");
		for(User u : userservice.findAllUsers()) {
			System.out.println(u.companyname());
		}
		
		return "personnel";
	}
	
	@GetMapping("/dashboard/companies")
	public String companylist( @AuthenticationPrincipal MyUserDetails user,Model model) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(user.getUser()));
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
	public String activity(@AuthenticationPrincipal MyUserDetails user,Model model) {
		Page<ActivityLog> actlog;
		if(user.getUser().role().equals("SUPERADMIN")) {
			actlog = actrepo.findAllByOrderByDateDesc(PageRequest.of(0, 10));
			model.addAttribute("activity", actlog);
		}
		else if(user.getUser().role().equals("MASTERADMIN")) {
			actlog = actrepo.findAllByCompanyOrderByDateDesc(user.getUser().getCompany(),PageRequest.of(0, 10));
			model.addAttribute("companyact", actlog);
		}
		else {
			actlog = actrepo.findByUserOrderByDateDesc(user.getUser(), PageRequest.of(0, 10));
			model.addAttribute("useractivity", actlog);		
		}
		model.addAttribute("totalpage", actlog.getTotalPages());
		model.addAttribute("currentPage", 1);
		
		return "activitylog";
	}
	
	@GetMapping("/dashboard/activitylog/page/{page}")
	public String activity(@AuthenticationPrincipal MyUserDetails user, Model model, @PathVariable("page") int page) {
		Page<ActivityLog> actlog;
		if(user.getUser().role().equals("SUPERADMIN")) {
			actlog = actrepo.findAllByOrderByDateDesc(PageRequest.of(page - 1, 10));
			model.addAttribute("activity", actlog);
		}
		else if(user.getUser().role().equals("MASTERADMIN")) {
			actlog = actrepo.findAllByCompanyOrderByDateDesc(user.getUser().getCompany(),PageRequest.of(page - 1, 10));
			model.addAttribute("companyact", actlog);
		}
		else {
			actlog = actrepo.findByUserOrderByDateDesc(user.getUser(), PageRequest.of(page - 1, 10));
			model.addAttribute("useractivity", actlog);		
		}
		model.addAttribute("totalpage", actlog.getTotalPages());
		model.addAttribute("currentPage", page);
		
		return "activitylog";
	}
	
	@GetMapping("/dashboard/notification")
	public String notifications(@AuthenticationPrincipal MyUserDetails user,Model model) {
		Page<Notification> notifs = notifrepo.findAllByUser(user.getUser(), PageRequest.of(0, 10 , Sort.by("Id").descending()));
		model.addAttribute("notifications", notifs);
		model.addAttribute("totalpage", notifs.getTotalPages());
		model.addAttribute("currentPage", 1);
		for(Notification n : notifs) {
			n.setSeen(true);
			notifrepo.save(n);
		}
		
		return "notifications";
	}
	
	@GetMapping("/dashboard/notification/page/{page}")
	public String notifications(@AuthenticationPrincipal MyUserDetails user, Model model,@PathVariable("page") int page) {
		Page<Notification> notifs = notifrepo.findAllByUser(user.getUser(), PageRequest.of(page - 1, 10 , Sort.by("Id").descending()));
		model.addAttribute("notifications", notifs);
		model.addAttribute("totalpage", notifs.getTotalPages());
		model.addAttribute("currentPage", page);
		for(Notification n : notifs) {
			n.setSeen(true);
			notifrepo.save(n);
		}
		return "notifications";
	}
	
	@GetMapping("/dashboard/leaderboard")
	public String leaderboard(Model model, @AuthenticationPrincipal MyUserDetails userdetail) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(userdetail.getUser()));
		model.addAttribute("yearmonth", YearMonth.now(ZoneId.of("Asia/Manila")));
		return "leaderboard";
	}
	
	
}
