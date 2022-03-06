package com.oikostechnologies.schedsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

	
	@GetMapping("/mytask/addtask")
	public String addMyTask() {
		return "addmytask";
	}
	
	@GetMapping("/assignedtask/addtask")
	public String addAssignedTask() {
		return "addpnltask";
	}
	
	
}
