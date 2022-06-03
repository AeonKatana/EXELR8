package com.oikostechnologies.schedsys.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.leadprojection.LeadDTO;
import com.oikostechnologies.schedsys.leadprojection.LeadUserDTO;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.NotificationRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;

@Controller
@RequestMapping("/leaderboard")
public class LeaderboardController {
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private NotificationRepo notifrepo;
	
	@GetMapping("/overdue")
	public String overdue(Model model, @AuthenticationPrincipal MyUserDetails userdetail) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(userdetail.getUser()));
		return "overleadboard";
	}
	
	@GetMapping("/tardy")
	public String tardy(Model model, @AuthenticationPrincipal MyUserDetails userdetail) {
		model.addAttribute("notifcount", notifrepo.countBySeenFalseAndUser(userdetail.getUser()));
		return "tardylead";
	}
	
	@GetMapping("/company/tardy")
	@ResponseBody
	public List<LeadDTO> getCompanyTardy(){
		return companyRepo.tardylead();
	}
	
	@GetMapping("/user/tardy")
	@ResponseBody
	public List<LeadUserDTO> getUserTardy(){
		return userRepo.tardylead();
	}
	
	@GetMapping("/company/overdue")
	@ResponseBody
	public List<LeadDTO> getOverdueCompanyLead(){
		return companyRepo.overlead(LocalDate.now(ZoneId.of("Asia/Manila")));
	}
	
	@GetMapping("/company")
	@ResponseBody
	public List<LeadDTO> getCompanyLeaderboard(@RequestParam(required = false) YearMonth yearmonth){
		if(yearmonth == null) {
			yearmonth = YearMonth.now(ZoneId.of("Asia/Manila"));
		}
		
		return companyRepo.leaderboard(yearmonth.getMonthValue(), yearmonth.getYear());
	}
	
	@GetMapping("/user/overdue")
	@ResponseBody
	public List<LeadUserDTO> getOverdueUserLead(@AuthenticationPrincipal MyUserDetails user){
		if(user.getUser().getCompany() == null) {
			return userRepo.overleaderboard(LocalDate.now(ZoneId.of("Asia/Manila")));
		}
			return userRepo.overleaderboard(LocalDate.now(ZoneId.of("Asia/Manila")), user.getUser().getCompany());
	}
	
	@GetMapping("/user")
	@ResponseBody
	public List<LeadUserDTO> getUserLeaderboard(@AuthenticationPrincipal MyUserDetails user,@RequestParam(required = false) YearMonth yearmonth){
		if(yearmonth == null) {
			yearmonth = YearMonth.now(ZoneId.of("Asia/Manila"));
		}
		if(user.getUser().getCompany() == null) {
			return userRepo.leaderboard(yearmonth.getMonthValue(), yearmonth.getYear());
		}
			return userRepo.leaderboard(yearmonth.getMonthValue(), yearmonth.getYear(),user.getUser().getCompany());
		
		
	}

}
