package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;

public interface UserDepartmentRepo extends JpaRepository<UserDepartment, Long> {

	UserDepartment findByUserAndDepartment(User user, Department department);
	List<UserDepartment> findByDepartment(Department d);
	UserDepartment findByUser(User user);
}
