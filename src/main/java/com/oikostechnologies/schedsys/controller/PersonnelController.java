package com.oikostechnologies.schedsys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.Role;
import com.oikostechnologies.schedsys.entity.Scorecard;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;
import com.oikostechnologies.schedsys.entity.UserRole;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.model.PersonnelModel;
import com.oikostechnologies.schedsys.model.ScoreCardModel;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.RoleRepo;
import com.oikostechnologies.schedsys.repo.ScoreCardRepo;
import com.oikostechnologies.schedsys.repo.UserDepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.repo.UserRoleRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.UserService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController {

	@Autowired
	private UserService userservice;
	
	
	@Autowired
	private ScoreCardRepo scorerepo;
	
	@Autowired
	private UserDepartmentRepo repo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
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
	
	

	@GetMapping("/allProjleader/{company}")
	@ResponseBody
	public ResponseEntity<List<PeopleModel>> getAllProjlead(@AuthenticationPrincipal MyUserDetails userdetail,@PathVariable("company") String company){
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getRolename().equals("SUPERADMIN")) {
			
			Company comp = companyRepo.findByCompname(company);
			if(comp == null) {
				return new ResponseEntity<List<PeopleModel>>(HttpStatus.NOT_FOUND);
			}
			else {
				for(User u : userRepo.getAllProjectLeaderByCompany(comp.getCompname())) {
					mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
				}
			}
		}
		
		else {
			for(User u : userRepo.getAllProjectLeaderByCompany(userdetail.getUser().companyname())) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
			
		return new ResponseEntity<List<PeopleModel>>(mention,HttpStatus.OK);
	}
	
	
	@GetMapping("/allSupervisor/{company}")
	@ResponseBody
	public ResponseEntity<List<PeopleModel>> getAllSupervisor(@AuthenticationPrincipal MyUserDetails userdetail,@PathVariable("company") String company){
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getRolename().equals("SUPERADMIN")) {
			
			Company comp = companyRepo.findByCompname(company);
			if(comp == null) {
				return new ResponseEntity<List<PeopleModel>>(HttpStatus.NOT_FOUND);
			}
			else {
				for(User u : userRepo.getAllSupervisorByCompany(comp.getCompname())) {
					mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
				}
			}
		}
		
		else {
			for(User u : userRepo.getAllSupervisorByCompany(userdetail.getUser().companyname())) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
			
		return new ResponseEntity<List<PeopleModel>>(mention,HttpStatus.OK);
	}
	
	@GetMapping("/allAssociate/{company}")
	@ResponseBody
	public ResponseEntity<List<PeopleModel>> getAllAssociate(@AuthenticationPrincipal MyUserDetails userdetail, @PathVariable("company") String company){
		
		List<PeopleModel> mention = new ArrayList<>();
		if(userdetail.getRolename().equals("SUPERADMIN")) {
			
			Company comp = companyRepo.findByCompname(company);
			if(comp == null) {
				return new ResponseEntity<List<PeopleModel>>(HttpStatus.NOT_FOUND);
			}
			else {
				for(User u : userRepo.getAllAssociateByCompany(comp.getCompname())) {
					mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
				}
			}
		}
		
		else {
			for(User u : userRepo.getAllAssociateByCompany(userdetail.getUser().companyname())) {
				mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
			}
		}
			
		return new ResponseEntity<List<PeopleModel>>(mention,HttpStatus.OK);
	}

	@GetMapping("/people/{company}")
	@ResponseBody
	public ResponseEntity<List<PeopleModel>> getMention(@AuthenticationPrincipal MyUserDetails userdetail, @PathVariable("company") String company){
		List<PeopleModel> mention = new ArrayList<>();
		
		for(User u : userservice.getAllByCompany(company)) {
			mention.add(new PeopleModel(u.getId(),u.fullname(),"people"));
		}
		return new ResponseEntity<List<PeopleModel>>(mention,HttpStatus.OK);
	}
	
	@PostMapping("/savecard") // Just a demo how to handle forbidden actions .....(not anymore)
	@ResponseBody
	public ResponseEntity<String> saveCard(@AuthenticationPrincipal MyUserDetails detail, ScoreCardModel model) {
		if(!detail.getUser().role().equalsIgnoreCase("MASTERADMIN") && !detail.getUser().role().equalsIgnoreCase("SUPERADMIN")) {
			return new ResponseEntity<String>("You dont have the power", HttpStatus.FORBIDDEN);
		}
		System.out.println("User ID :" + model.getUserid());
		System.out.println("Role :" + model.getRole());
		
		User user = userRepo.findById(model.getUserid()).orElse(null);
		Scorecard card = scorerepo.findByUser(user);
		Role r = roleRepo.findByRolename(model.getRole());
		
		if(r == null || user == null) {
		   return new ResponseEntity<String>("User Not found",HttpStatus.NOT_FOUND);
		}
		UserRole ur = userRoleRepo.findByUser(user);
		if(card == null) {
			card = new Scorecard();
			card.setMainscorecard(model.getMainscorecard());
			card.setCorecompetencies(model.getCorecompetencies());
			card.setDefinition(model.getDefinition());
			card.setEducationalbg(model.getEducationalbg());
			card.setIndicators(model.getIndicators());
			card.setMetrics(model.getMetrics());
			card.setPerforaccel(model.getPerforaccel());
			card.setRoledesc(model.getRoledesc());
			ur.setRole(r);
			userRoleRepo.save(ur);
			card.setUser(user);
			scorerepo.save(card);
		}else {
			card.setMainscorecard(model.getMainscorecard());
			card.setCorecompetencies(model.getCorecompetencies());
			card.setDefinition(model.getDefinition());
			card.setEducationalbg(model.getEducationalbg());
			card.setIndicators(model.getIndicators());
			card.setMetrics(model.getMetrics());
			card.setPerforaccel(model.getPerforaccel());
			card.setRoledesc(model.getRoledesc());
			ur.setRole(r);
			userRoleRepo.save(ur);
			card.setUser(user);
			scorerepo.save(card);
		}
		
		return new ResponseEntity<String>("Success",HttpStatus.OK);
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
