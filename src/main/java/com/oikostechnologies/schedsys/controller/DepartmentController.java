package com.oikostechnologies.schedsys.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.Notification;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;
import com.oikostechnologies.schedsys.model.DeptModel;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.NotificationRepo;
import com.oikostechnologies.schedsys.repo.UserDepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.DailyTaskService;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentRepo deptrepo;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private UserDepartmentRepo udrepo;
	
	@Autowired
	private DailyTaskService dailyservice;
	
	@Autowired
	private NotificationRepo notifrepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@PostMapping("/addDeptUser")
	@ResponseBody
	public ResponseEntity<String> addDeptUser(@AuthenticationPrincipal MyUserDetails detail, @RequestBody DeptModel model) {
		
		
		
		Department d = deptrepo.findByDeptname(model.getDeptname());
		
		
		if(d == null) {
			return new ResponseEntity<String>("Department does not exist",HttpStatus.NOT_FOUND);
		}
		// MASTERADMIN but does not belong to this company
		if(!detail.getRolename().equals("SUPERADMIN") && !detail.getUser().companyname().equals(d.companyname())) {
			return new ResponseEntity<String>("Unauthorized Action",HttpStatus.UNAUTHORIZED);
		}
		
		for(PeopleModel pm : model.getPeople()) {
			User user = repo.findById(pm.getId()).orElse(null);
			UserDepartment exists = udrepo.findByUserAndDepartment(user,d);
			if(exists == null) {
				UserDepartment ud = new UserDepartment();
				ud.setDepartment(d);
				ud.setDeptrole("MEMBER");
				ud.setUser(user);
				System.out.println("HI!?");
				udrepo.save(ud);
		     }
		}
		return new ResponseEntity<String>("Members successfully added",HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/kickUser/{id}")
	@ResponseBody
	@Transactional
	public ResponseEntity<String> kickUser(@AuthenticationPrincipal MyUserDetails detail ,@PathVariable("id") long id, @RequestParam("deptid") long deptid) {
		User user = repo.findById(id).orElse(null);
		Department d = deptrepo.findById(deptid).orElse(null);
		
		if(user == null || d == null) {
			return new ResponseEntity<String>("User or Department not Found",HttpStatus.NOT_FOUND);
		}
		// MASTERADMIN but does not belong to this company
				if(!detail.getRolename().equals("SUPERADMIN") && !detail.getUser().companyname().equals(d.companyname())) {
					return new ResponseEntity<String>("Unauthorized Action",HttpStatus.UNAUTHORIZED);
		}
		
		Notification notification = new Notification();
		notification.setAction("has kicked you from the department");
		notification.setActionuser(detail.getUser());
		notification.setUser(user);
		notification.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
		notification.setActiontarget(d.getDeptname());
		notification.setTargetlink("/department/" + d.getId());
		
		notifrepo.save(notification);
		
		UserDepartment ud = udrepo.findByUserAndDepartment(user, d);
		udrepo.delete(ud);
		
		return new ResponseEntity<String>("User successfully kicked",HttpStatus.OK);
	}
	
	@PostMapping("/savedept")
	@ResponseBody
	public ResponseEntity<String> saveDepartment(@AuthenticationPrincipal MyUserDetails detail, @RequestBody DeptModel model) {
		Department d = new Department();
		d.setDeptname(model.getDeptname());
		Company company = companyRepo.findByCompname(model.getCompanyname());
		Department department = deptrepo.findByDeptnameAndCompany(model.getDeptname(), company);
		if(detail.getRolename().equals("SUPERADMIN")) {
			System.out.println("Company :" + model.getCompanyname());
			if(company == null) {
				return new ResponseEntity<String>("Company does not exist", HttpStatus.NOT_FOUND);
			}
			else if(department != null) {
				return new ResponseEntity<String>("This department already exist",HttpStatus.CONFLICT);
			}
			d.setCompany(company);			
		}
		else {
			Department dept = deptrepo.findByDeptnameAndCompany(model.getDeptname(), detail.getUser().getCompany());
			if(dept != null) {
				return new ResponseEntity<String>("This department already exist",HttpStatus.CONFLICT);
			}
			 d.setCompany(detail.getUser().getCompany());
		}
		  
		deptrepo.save(d);
		
		for(PeopleModel pm : model.getPeople()) {
			System.out.println("Name : " + pm.getName());
			User user = repo.findById(pm.getId()).orElse(null);
				UserDepartment ud = new UserDepartment();
				ud.setDepartment(d);
				ud.setDeptrole("MEMBER");
				ud.setUser(user);
				udrepo.save(ud);
		     
		}
		
		return new ResponseEntity<String>("Department Created" , HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public String viewDepartment(HttpServletRequest request,@PathVariable("id") long deptid, Model model, @AuthenticationPrincipal MyUserDetails user) {
		HttpSession session = request.getSession(true);
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(user.getUser()));
		Department d = deptrepo.findById(deptid).orElse(null);
		session.setAttribute("dep", d);
		if(d == null) {
			return "redirect:/dashboard/department";
		}
		List<UserDepartment> userdept = new ArrayList<>(d.getUserdepartment()); // Get all users of this department
		UserDepartment ud = udrepo.findByUserAndDepartment(user.getUser(), d); // Find the current logged-in user if he belongs to this department
		model.addAttribute("currUser", user.getUser());
		model.addAttribute("department", d);
		model.addAttribute("deptuser", ud);
		model.addAttribute("users", userdept);
		model.addAttribute("today", ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
		model.addAttribute("dailycount", dailyservice.countDailyByDeparment(d.getDeptname()));
		model.addAttribute("overduecount", dailyservice.countOverdueByDepartment(d.getDeptname()));
		return "viewdepartment";
	}
	
	@GetMapping("/{id}/trash")
	public String trash(@PathVariable("id") long deptid, Model model, @AuthenticationPrincipal MyUserDetails user) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(user.getUser()));
		Department d = deptrepo.findById(deptid).orElse(null);
		if(d == null) {
			return "redirect:/dashboard/department";
		}
		model.addAttribute("department", d);
		model.addAttribute("title", d.getDeptname() + "'s Archives");
		return "trash";
	}
	
	@GetMapping("/{id}/taskTrash")
	@ResponseBody
	public List<DailyTask> trashes(@PathVariable("id") long deptid){
		Department d = deptrepo.findById(deptid).orElse(null);
		if(d == null) {
			return Collections.emptyList();
		}
		return dailyservice.findAllDeletedTaskByDepartment(d);
	}
	@GetMapping("/{id}/taskDone")
	@ResponseBody
	public List<DailyTask> doneTasks(@PathVariable("id") long deptid){
		Department d = deptrepo.findById(deptid).orElse(null);
		if(d == null) {
			return Collections.emptyList();
		}
		return dailyservice.findAllDoneTaskByDepartment(d);
	}
	
	// DataTable 
	
	@GetMapping("/comdept/{company}")
	@ResponseBody
	public ResponseEntity<List<Department>> comdept(@AuthenticationPrincipal MyUserDetails user, @PathVariable("company") String company){
		Company comp = companyRepo.findByCompname(company);
		if(comp == null) {
			return new ResponseEntity<List<Department>>(HttpStatus.NOT_FOUND);
		}
		List<Department> department =  deptrepo.findAllByCompany(comp);
		return new ResponseEntity<List<Department>>(department,HttpStatus.OK);
	}
	
	@GetMapping("/departments")
	@ResponseBody
	public List<Department> departments(@AuthenticationPrincipal MyUserDetails user){
		if(user.getUser().role().equals("SUPERADMIN")) {
			return deptrepo.findAll();
		}
		else if(user.getRolename().equals("MASTERADMIN")) {
			return deptrepo.findAllByCompany(user.getUser().getCompany());
		}
		else {
			return deptrepo.getAllByCompanyOrUser(user.getUser().getCompany(),user.getUser());
		}
	}
	@Transactional
	@DeleteMapping("/deleteDepartment/{id}")
	public ResponseEntity<String> deleteDepartment(@AuthenticationPrincipal MyUserDetails user,@PathVariable("id") long id) {
		if(user.getRolename().equals("SUPERADMIN") || user.getRolename().equals("MASTERADMIN")) {
			 deptrepo.deleteById(id);
			 return new ResponseEntity<String>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("You don't have the power",HttpStatus.FORBIDDEN);
		}
	}
	
}
