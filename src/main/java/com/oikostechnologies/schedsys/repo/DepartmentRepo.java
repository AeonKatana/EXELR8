package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

	@Query("SELECT d from Department d join d.userdepartment ud join ud.user u where u =:user")
	List<Department> getAllByUser(@Param("user") User user);
	
	Department findByDeptname(String deptname);
	
	@Query("SELECT DISTINCT d from Department d join d.userdepartment ud join ud.user u left join d.company c where c =:company OR u =:user")
	List<Department> getAllByCompanyOrUser(@Param("company")Company company,@Param("user") User user);
	
	List<Department> findAllByCompany(Company company);
	
	Department findByDeptnameAndCompany(String deptname,Company company);
}
