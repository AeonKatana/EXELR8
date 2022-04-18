package com.oikostechnologies.schedsys.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.model.DailyTaskModel;
import com.oikostechnologies.schedsys.repo.DailyTaskRepo;
import com.oikostechnologies.schedsys.repo.NotifyUserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.DailyTaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private DailyTaskService dailyservice;
	
	
	
	
	@GetMapping("/mytask/addtask")
	public String addMyTask(Model model) {
		return "addmytask";
	}
	
	@GetMapping("/assignedtask/addtask")
	public String addAssignedTask() {
		return "addpnltask";
	}
	
	
	@PutMapping("/edittask/{id}")
	@ResponseBody
	public String editTask(@PathVariable("id") long id, @RequestBody DailyTaskModel dailyedit, @AuthenticationPrincipal MyUserDetails user) {
		return dailyservice.editTask(id, dailyedit, user.getUser());
	}
	
	@GetMapping("/getTask/{id}")
	@ResponseBody
	public DailyTaskModel getTask(@PathVariable("id") long id) {
		return dailyservice.getTask(id);
	}
	
	
	@PostMapping("/savemytask")
	@ResponseBody
	public DailyTaskModel saveMyTask(@RequestBody DailyTaskModel daily , @AuthenticationPrincipal MyUserDetails userdetails) {
		
		DailyTaskModel task = dailyservice.addMyTask(daily , userdetails.getUser());
		
		return task;
	}
	
	@PostMapping("/markasdone")
	@ResponseBody
	public String markAsDone(@AuthenticationPrincipal MyUserDetails user,@RequestParam("status") boolean status, @RequestParam("id") long taskid) {
		return dailyservice.markAsDone(user.getUser(), status, taskid);
	}
	
	@Transactional
	@DeleteMapping("/deleteTask")
	@ResponseBody
	public String deleteTask(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") long taskid) {
		
		
		return dailyservice.deleteTask(user.getUser(), taskid);
		
	}
	
	@GetMapping("/searchtask")
	public String searchTask(@AuthenticationPrincipal MyUserDetails user,@RequestParam("search") String search, Model model) {
		model.addAttribute("currentUser", user.getUser().getId());
		model.addAttribute("mytask", dailyservice.searchTask(search));
		model.addAttribute("currentDate", ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
		return "task";
	}
	
	
//	@PostMapping("/mentionTest")
//	@ResponseBody
//	public DailyTaskModel mention(@RequestBody DailyTaskModel daily) {
//		
//		for(PeopleModel pm : daily.getMentions()) {
//			System.out.println("ID :" + pm.getId());
//			System.out.println("Name : " + pm.getName());
//		}
//		
//		return daily;
//	}
	
	
	
	
}
