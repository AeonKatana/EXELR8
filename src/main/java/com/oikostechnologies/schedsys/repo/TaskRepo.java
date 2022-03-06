package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

}
