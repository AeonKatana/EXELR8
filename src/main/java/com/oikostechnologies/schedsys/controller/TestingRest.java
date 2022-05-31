package com.oikostechnologies.schedsys.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.service.DailyTaskService;

@RestController
public class TestingRest {

	@Autowired
	private DailyTaskService dailyservice;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CompanyRepo comprepo;
	
	@Autowired
	private DepartmentRepo deptrepo;
	
	@GetMapping("/getAllTask/{name}")
	public List<DailyTask> getAllTask(@PathVariable("name") String name){
		return dailyservice.getAllTask();
	}
	
	@Transactional
	@DeleteMapping("/deleteMasteadmin")
	public void deleteMasteradmin() {
		userRepo.deleteById(7L);
	}
	
	@Transactional
	@DeleteMapping("/deletAllCompany")
	public void deleteAllCompany() {
		comprepo.deleteAll();
	}
	@Transactional
	@DeleteMapping("/deleteCompany/{id}")
	public void deleteCompany(@PathVariable("id") long id) {
		comprepo.deleteById(id);
	}
}
