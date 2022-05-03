package com.oikostechnologies.schedsys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.model.PersonnelModel;
import com.oikostechnologies.schedsys.repo.UserDepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.UserService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController {

	@Autowired
	private UserService userservice;
	
	
	
	@Autowired
	private UserDepartmentRepo repo;
	
	@Autowired
	private UserRepo userRepo;
	
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
	public List<User> personnelList(){
		return userservice.findAll();
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
	
	@GetMapping("/deptpeople")
	@ResponseBody
	public List<PeopleModel> departmentPeople(HttpSession session,@AuthenticationPrincipal MyUserDetails userdetail){
		List<PeopleModel> mention = new ArrayList<>();
		
		for(UserDepartment u : repo.findByDepartment((Department)session.getAttribute("dep"))) {
			
			mention.add(new PeopleModel(u.getUser().getId(),u.getUser().fullname(),"people"));
			
		}
		
		return mention;
		
	}
	
	@GetMapping("/allProjleader")
	@ResponseBody
	public List<PeopleModel> getAllProjlead(@AuthenticationPrincipal MyUserDetails userdetail){
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			for(User u : userRepo.getAllProjectLeader()) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		else {
			for(User u : userRepo.getAllProjectLeaderByCompany(userdetail.getUser().companyname())) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		return mention;
	}
	
	
	@GetMapping("/allSupervisor")
	@ResponseBody
	public List<PeopleModel> getAllSupervisor(@AuthenticationPrincipal MyUserDetails userdetail){
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			for(User u : userRepo.getAllSupervisor()) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		else {
			for(User u : userRepo.getAllSupervisorByCompany(userdetail.getUser().companyname())) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		return mention;
	}
	
	@GetMapping("/allAssociate")
	@ResponseBody
	public List<PeopleModel> getAllAssociate(@AuthenticationPrincipal MyUserDetails userdetail){
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			for(User u : userRepo.getAllAssociate()) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		else {
			for(User u : userRepo.getAllAssociateByCompany(userdetail.getUser().companyname())) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
		return mention;
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
	
	@PostMapping("/addPersonnel")
	@ResponseBody
	public boolean addPersonnel(@AuthenticationPrincipal MyUserDetails user, PersonnelModel model ,HttpServletRequest request) {
		return userservice.addPersonnel(user, model, request);
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
