package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.UserTask;

public interface UserTaskRepo extends JpaRepository<UserTask, Long> {

}
