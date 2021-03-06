package com.oikostechnologies.schedsys.service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.UserRepo;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SchedulerService {

	@Autowired
	private UserRepo repo;
	 
	private List<User> tardyusers = Collections.emptyList();
	
	@Scheduled(cron = "0 30 15 * * 1-6", zone = "Asia/Manila") // Run everyday at 9am except Sunday
	public void checkTardy() {
		LocalDate date = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(); // Get the date today
		tardyusers = repo.getAllTardyUsers(Date.valueOf(date));
		System.out.println("Calling cron service...");
		System.out.println("-------------------Tardy Users----------------");
		for(User u : tardyusers) {
//			u.setViolationcount(u.getViolationcount() + 1); // Increase violation count if tardy
//			repo.save(u);
			System.out.println(u.fullname());
		}
		
	}
	public void setTardy(List<User> user) {
		tardyusers = user;
	}
	
	public List<User> getTardyUsers(){
		return tardyusers;
	}
	
}
