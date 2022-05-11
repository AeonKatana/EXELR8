package com.oikostechnologies.schedsys.controller;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.leadprojection.LeadDTO;
import com.oikostechnologies.schedsys.leadprojection.LeadUserDTO;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;

@Controller
@RequestMapping("/leaderboard")
public class LeaderboardController {
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/company")
	@ResponseBody
	public List<LeadDTO> getCompanyLeaderboard(@RequestParam(required = false) YearMonth yearmonth){
		if(yearmonth == null) {
			yearmonth = YearMonth.now(ZoneId.of("Asia/Manila"));
		}
		System.out.println(yearmonth.getMonthValue());
		return companyRepo.leaderboard(yearmonth.getMonthValue(), yearmonth.getYear());
	}
	
	@GetMapping("/user")
	@ResponseBody
	public List<LeadUserDTO> getUserLeaderboard(@RequestParam(required = false) YearMonth yearmonth){
		if(yearmonth == null) {
			yearmonth = YearMonth.now(ZoneId.of("Asia/Manila"));
		}
		return userRepo.leaderboard(yearmonth.getMonthValue(), yearmonth.getYear());
	}

}
