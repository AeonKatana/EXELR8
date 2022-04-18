package com.oikostechnologies.schedsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;
import com.oikostechnologies.schedsys.model.DeptModel;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserDepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
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
	
	
	@PostMapping("/savedept")
	@ResponseBody
	public String saveDepartment(@AuthenticationPrincipal MyUserDetails detail, @RequestBody DeptModel model) {
		
		Department d = new Department();
		d.setDeptname(model.getDeptname());
		d.setCompany(detail.getUser().getCompany());
		
		deptrepo.save(d);
		
		UserDepartment ud2 = new UserDepartment();
		ud2.setUser(detail.getUser());
		ud2.setDepartment(d);
		udrepo.save(ud2);
		
		for(PeopleModel pm : model.getPeople()) {
			User user = repo.findById(pm.getId()).orElse(null);
			UserDepartment ud = new UserDepartment();
			ud.setDepartment(d);
			ud.setUser(user);
			udrepo.save(ud);
		}
		
		
		
		return "Department Added!";
	}
	
	
}
