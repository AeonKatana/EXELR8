package com.oikostechnologies.schedsys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.UserService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController {

	@Autowired
	private UserService userservice;
	
/**	
 * Manual Pagination and Search functionality
 *  
 * @GetMapping("/search")
	public String searchPersonnel(Model model , @RequestParam("search") String search) {
		model.addAttribute("parameter", search);
		model.addAttribute("masteradmins", userservice.searchUser(search));
		model.addAttribute("totalelement", userservice.searchUser(search).getTotalElements());
		model.addAttribute("totalpage", userservice.searchUser(search).getTotalPages());
		return "personnel";
	}
	
	@GetMapping("/page/{page}")
	public String personnelList(Model model, @PathVariable("page") int page) {
		model.addAttribute("currentPage", page);
		model.addAttribute("masteradmins", userservice.findAllUsers(page - 1));
		model.addAttribute("totalelement", userservice.findAllUsers(page - 1).getTotalElements());
		model.addAttribute("totalpage", userservice.findAllUsers(page - 1).getTotalPages());;
		
		return "personnel";
	}
	
	@GetMapping("/page/{page}/search")
	public String personnelList(Model model, @PathVariable("page") int page , @RequestParam("search") String search) {
		model.addAttribute("currentPage", page);
		model.addAttribute("parameter", search);
		model.addAttribute("masteradmins", userservice.findAllUsers(page - 1, search));
		model.addAttribute("totalelement", userservice.findAllUsers(page - 1, search).getTotalElements());
		model.addAttribute("totalpage", userservice.findAllUsers(page - 1, search).getTotalPages());;
		
		return "personnel";
	}
**/
	@GetMapping("/datatable")   // End point for DataTables in JQUERY AJAX
	@ResponseBody
	public DataTablesOutput<User> personnelList(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParams){
		return userservice.findAll(input);
	}
	
	@GetMapping("/mycompanypeople")
	@ResponseBody
	public List<User> personnelList(@AuthenticationPrincipal MyUserDetails userdetail){
		System.out.println(userdetail.getFullname());
		for(User u : userservice.getAllByCompany(userdetail)){
			System.out.println("Name :" + u.getFirstname());
		}
		return userservice.getAllByCompany(userdetail);
	}
	
	@GetMapping("/companypeople")
	@ResponseBody
	public List<User> companyPeople(HttpSession session){
		String companyname = (String)session.getAttribute("companyview");
		System.out.println("Company Name :" + companyname);
		return userservice.getAllByCompany(companyname);
	}
	
	@GetMapping("/people")
	@ResponseBody
	public List<PeopleModel> getMention(@AuthenticationPrincipal MyUserDetails userdetail){
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			for(User u : userservice.findAllUsers()) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		
		else {
		for(User u : userservice.getAllByCompany(userdetail)) {
			mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
		}
		}
		return mention;
	}
	
	@PostMapping("/savecard") // Just a demo how to handle forbidden actions 
	@ResponseBody
	public String saveCard(@AuthenticationPrincipal MyUserDetails detail) {
		if(!detail.getUser().role().equalsIgnoreCase("MASTERADMIN") && !detail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			return "You're not powerful enough to use this. Please consult your boss";
		}
		return "Scorecard Added!";
	}
	
	@GetMapping("/getDetail")
	@ResponseBody
	public User getDetail(@RequestParam("id") long id) {
		
		User user = userservice.findById(id);
		if(user == null) {
			return null;
		}
		
		return user;
	}
	
	
}
