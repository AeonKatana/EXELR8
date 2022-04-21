package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

	
	List<Task> findByDepartmentOrderByIdDesc(Department d);
}
