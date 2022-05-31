package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;

public interface UserDepartmentRepo extends JpaRepository<UserDepartment, Long> {

	UserDepartment findByUserAndDepartment(User user, Department department);
	List<UserDepartment> findByDepartment(Department d);
	UserDepartment findByUser(User user);
	
	@Query("SELECT ud from UserDepartment ud join ud.department d join d.company c where c.compname =:compname and d.deptname =:deptname")
	UserDepartment getByDepartmentAndCompany(@Param("deptname") String deptname, @Param("compname") String compname);
}
