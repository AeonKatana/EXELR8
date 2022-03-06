package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oikostechnologies.schedsys.entity.TaskDetail;

public interface TaskDetailRepo extends JpaRepository<TaskDetail, Long>{

	@Query("select count(*) from TaskDetail where done = 1")
	int countCompleted();
	
}
