package com.oikostechnologies.schedsys.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpSession;
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

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.Task;
import com.oikostechnologies.schedsys.entity.TaskDetail;
import com.oikostechnologies.schedsys.entity.UserTask;
import com.oikostechnologies.schedsys.model.DailyTaskModel;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.model.TaskModel;
import com.oikostechnologies.schedsys.repo.TaskDetailRepo;
import com.oikostechnologies.schedsys.repo.TaskRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.repo.UserTaskRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.DailyTaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private DailyTaskService dailyservice;
	
	@Autowired
	private TaskRepo taskrepo;
	
	@Autowired
	private TaskDetailRepo detailrepo;
	
	
	@Autowired
	private UserTaskRepo usertaskrepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/mytask/addtask")
	public String addMyTask(Model model) {
		return "addmytask";
	}
	
	@PostMapping("/multiAdd")
	@ResponseBody
	public DailyTaskModel saveMultiTask(@RequestBody DailyTaskModel daily, @AuthenticationPrincipal MyUserDetails user) {
		return dailyservice.addMultiTask(daily, user.getUser());
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
	
	
	@PostMapping("/addTaskDetail")
	@ResponseBody
	public boolean addTaskDetail(HttpSession session,@RequestBody TaskModel model) {
		
		Task task = taskrepo.findById(model.getId()).orElse(null);
		if(task != null) {
			TaskDetail detail = new TaskDetail();
			detail.setDescription(model.getDetail());
			detail.setEdate(model.getEdate().atTime(0, 0, 0));
			detail.setSdate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
			detail.setTask(task);
			
			detailrepo.save(detail);
			
			for(PeopleModel u : model.getWho()) {
				UserTask usertask = new UserTask();
				usertask.setTaskdetail(detail);
				usertask.setUser(userRepo.findById(u.getId()).orElse(null));
				usertaskrepo.save(usertask);
			}
			return true;
		}
		return false;
	}
	
	@PostMapping("/addDepttask")
	@ResponseBody
	public boolean addDeptTask(HttpSession session,@RequestBody TaskModel model) {
		
		
		Task task = new Task();
		task.setTaskname(model.getTaskname());
		task.setDepartment((Department)session.getAttribute("dep"));
		
		taskrepo.save(task);
		
		TaskDetail detail = new TaskDetail();
		detail.setDescription(model.getDetail());
		detail.setEdate(model.getEdate().atTime(0, 0, 0));
		detail.setSdate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		detail.setTask(task);
		
		detailrepo.save(detail);
		
		for(PeopleModel u : model.getWho()) {
			UserTask usertask = new UserTask();
			usertask.setTaskdetail(detail);
			usertask.setUser(userRepo.findById(u.getId()).orElse(null));
			usertaskrepo.save(usertask);
		}
		return true;
	}
	
	@PutMapping("/markdetaildone/{id}")
	@ResponseBody
	public String check(@PathVariable("id") long id,@RequestParam("check") boolean check) {
		
		TaskDetail detail = detailrepo.findById(id).orElse(null);
		if(detail == null) {
			return "An Error Occured. Please try again";
		}
		detail.setDone(check);
		detailrepo.save(detail);
		return "Task Done!";
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
