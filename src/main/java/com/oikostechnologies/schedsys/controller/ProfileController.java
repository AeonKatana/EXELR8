package com.oikostechnologies.schedsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.DailyTaskRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;

@Controller
public class ProfileController {

	@Autowired
	private UserRepo userepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ActlogRepo actrepo;
	
	@Autowired
	private DailyTaskRepo dailyrepo;
	
	
	
	@GetMapping("/profile/{id}")
	public String viewProfile(@AuthenticationPrincipal MyUserDetails user, Model model, @PathVariable("id") long id) {
		
		
		if(id == user.getUser().getId()) {
			return "redirect:/profile";
		}
		
		User viewuser = userepo.findById(id).orElse(null);
		
		model.addAttribute("viewuser", viewuser);
		model.addAttribute("activity", actrepo.findByUserOrderByDateDesc(viewuser));
		model.addAttribute("mytask", dailyrepo.findAllByDoneFalseAndUserOrderById(viewuser));
		
		return "viewprofile";
		
	}
	
	@GetMapping("/profile")
	public String profile(@AuthenticationPrincipal MyUserDetails user,@ModelAttribute("updatemsg") String msg,Model model) {
		model.addAttribute("message", msg);
		model.addAttribute("currentUser", user.getUser());
		model.addAttribute("activity", actrepo.findByUserOrderByDateDesc(user.getUser()));
		model.addAttribute("mytask", dailyrepo.findAllByDoneFalseAndUserOrderById(user.getUser()));
		return "settings";
	}
	
	@PostMapping("/profile/updateInfo")
	public String updateProfile(@AuthenticationPrincipal MyUserDetails user ,User edituser, RedirectAttributes attr) {
	
		
			User edited = userepo.findById(user.getUser().getId()).orElse(null);
			edited.setFirstname(edituser.getFirstname());
			edited.setLastname(edituser.getLastname());
			edited.setEmail(edituser.getEmail());
			edited.setContactno(edituser.getContactno());
			userepo.save(edited);
			
			user.getUser().setFirstname(edited.getFirstname());
			user.getUser().setLastname(edited.getLastname());
			user.getUser().setContactno(edited.getContactno());
			user.getUser().setEmail(edited.getEmail());
			attr.addFlashAttribute("updatemsg", "Updated Succesfully");
			return "redirect:/profile";
	}
	
	@PostMapping("/profile/updatePassword")
	@ResponseBody
	public String updatePassword(@AuthenticationPrincipal MyUserDetails user,@RequestParam("currentPass") String currentPass, @RequestParam("password") String password) {

		String mypass = user.getPassword();
		System.out.println("My Password is " + mypass);
		System.out.println("My typed password is " + currentPass);
		
		if(encoder.matches(currentPass, mypass)) {
			System.out.println("The passwords matched!");
			User currentUser = userepo.findById(user.getUser().getId()).orElse(null);
			currentUser.setPassword(encoder.encode(password).toCharArray());
			userepo.save(currentUser);
			return "Your password has been successfully changed";
		}
		else {
			System.out.println("The passwords didn't matched..");
			return "Your current password is incorrect. Please try again";
		}
		
		
	}
}
