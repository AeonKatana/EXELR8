package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.UserDepartment;

public interface UserDepartmentRepo extends JpaRepository<UserDepartment, Long> {

}
