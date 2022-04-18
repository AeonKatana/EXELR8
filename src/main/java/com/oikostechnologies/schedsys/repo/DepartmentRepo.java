package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

	@Query("SELECT d from Department d join d.userdepartment ud join ud.user u where u =:user")
	List<Department> getAllByUser(@Param("user") User user);
	
	
}
