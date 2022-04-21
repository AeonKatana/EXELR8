package com.oikostechnologies.schedsys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;
import com.oikostechnologies.schedsys.entity.UserTask;
import com.oikostechnologies.schedsys.model.DeptModel;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.TaskRepo;
import com.oikostechnologies.schedsys.repo.UserDepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.repo.UserTaskRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;

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
	private TaskRepo taskrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private UserTaskRepo userTaskRepo;
	
	@PostMapping("/addDeptUser")
	@ResponseBody
	public String addDeptUser(@AuthenticationPrincipal MyUserDetails detail, @RequestBody DeptModel model) {
		
		Department d = deptrepo.findByDeptname(model.getDeptname());
		if(d == null) {
			return "This department doesn't exist anymore";
		}
		for(PeopleModel pm : model.getPeople()) {
			User user = userrepo.findById(pm.getId()).orElse(null);
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
		return "Members Added!";
		
	}
	
	
	@DeleteMapping("/kickUser/{id}")
	@ResponseBody
	@Transactional
	public String kickUser(@PathVariable("id") long id, @RequestParam("deptid") long deptid) {
		User user = userrepo.findById(id).orElse(null);
		Department d = deptrepo.findById(deptid).orElse(null);
		
		if(user == null || d == null) {
			return "An error occured. Please try again";
		}
		
		UserDepartment ud = udrepo.findByUserAndDepartment(user, d);
		Set<UserTask> ut = ud.getUser().getTasks();
		for(UserTask t : ut) {
			System.out.println(t.getTaskdetail().getDescription());
			userTaskRepo.deleteById(t.getId());
		}
		udrepo.delete(ud);
		
		
		return "Member has been kicked out successfully";
	}
	
	@PostMapping("/savedept")
	@ResponseBody
	public String saveDepartment(@AuthenticationPrincipal MyUserDetails detail, @RequestBody DeptModel model) {
		
		Department department = deptrepo.findByDeptname(model.getDeptname());
		if(department == null) {
		Department d = new Department();
		d.setDeptname(model.getDeptname());
		d.setCompany(detail.getUser().getCompany());
		
		deptrepo.save(d);
		
		UserDepartment ud2 = new UserDepartment();
		ud2.setUser(detail.getUser());
		ud2.setDeptrole("SUPERVISOR");
		ud2.setDepartment(d);
		udrepo.save(ud2);
		
		for(PeopleModel pm : model.getPeople()) {
			User user = repo.findById(pm.getId()).orElse(null);
			
			UserDepartment exists = udrepo.findByUser(user);
			if(exists == null) {
				UserDepartment ud = new UserDepartment();
				ud.setDepartment(d);
				ud.setDeptrole("MEMBER");
				ud.setUser(user);
				udrepo.save(ud);
		     }
		}
		
		return "Department Added!";
		}
		return "This Department name already exists";
	}
	
	@GetMapping("/{department}")
	public String viewDepartment(HttpServletRequest request,@PathVariable("department") String department, Model model, @AuthenticationPrincipal MyUserDetails user) {
		HttpSession session = request.getSession(true);
	
		Department d = deptrepo.findByDeptname(department);
		session.setAttribute("dep", d);
		if(d == null) {
			return "redirect:/dashboard/department";
		}
		List<UserDepartment> userdept = new ArrayList<>(d.getUserdepartment());
		
		Set<UserTask> ut = user.getUser().getTasks();
		
		UserDepartment ud = udrepo.findByUserAndDepartment(user.getUser(), d);
		
		model.addAttribute("ut", ut);
		model.addAttribute("department", d);
		model.addAttribute("user", ud);
		model.addAttribute("supervisors", userdept.stream().filter(x -> x.getDeptrole().equals("SUPERVISOR")).collect(Collectors.toList()));
		model.addAttribute("users", userdept);
		model.addAttribute("task", taskrepo.findByDepartmentOrderByIdDesc(d));
		return "viewdepartment";
	}
	
	@PostMapping("/editDeptRole")
	@ResponseBody
	public boolean editDeptRole(@RequestParam("role") String role, @RequestParam("userid") long id,
			@RequestParam("deptid") long deptid){
		try {
			if(!role.equals("MEMBER") && !role.equals("SUPERVISOR")) {
				return false;
			}else {
				Department d = deptrepo.findById(deptid).orElse(null);
				User user = userrepo.findById(id).orElse(null);
			    UserDepartment ud = udrepo.findByUserAndDepartment(user, d);
			    ud.setDeptrole(role);
			    udrepo.save(ud);
		     }
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
}
