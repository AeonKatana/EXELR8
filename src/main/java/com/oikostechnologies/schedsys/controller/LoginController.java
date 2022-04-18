package com.oikostechnologies.schedsys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.PasswordToken;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.EmailModel;
import com.oikostechnologies.schedsys.repo.PasswordTokenRepo;
import com.oikostechnologies.schedsys.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordTokenRepo prepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/login-page")
	public String loginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginform";
        }
		return "redirect:/";
	}
	
	@RequestMapping("/perform-login")
	public String performLogin() {
		return "redirect:/";
	}
	
	@RequestMapping("/perform-logout")
	public String performLogout() {
		return "";
	}
	
	@RequestMapping("/forgot-pass")
	public String forgotPass() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "resetpasswordlink";
        } 
		return "redirect:/";
	}
	
	@RequestMapping("/forgot-pass/reset")
	public String resetPass() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "forgotpassword";
        }
		return "redirect:/";
	}
	
	@PostMapping("/forgot-pass/send")
	@ResponseBody
	public String sendEmail(EmailModel model, HttpServletRequest request) {
		return userService.findByEmail(model.getEmail() , request);
	}
	
	@PostMapping("/resetresult")
	@Transactional
	public String result(@RequestParam("token") String token, @RequestParam("password") String password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
        	PasswordToken ptoken = prepo.findByToken(token);
        	User user = ptoken.getUser();
        	user.setPassword(passwordEncoder.encode(password).toCharArray());
        	prepo.delete(ptoken);
            return "resetresult";
        }
		return "redirect:/";
	}
	
	@RequestMapping("/resetPass")
	public String resetPassPage(Model model, @RequestParam("token") String token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
        	PasswordToken ptoken = prepo.findByToken(token);
        	if(ptoken == null) {
        		model.addAttribute("token", null);
        	}
        	else
        		model.addAttribute("token",token);
            return "resetpassword";
        }
		return "redirect:/";
	}
	
}
