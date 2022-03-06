package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Role;
import com.oikostechnologies.schedsys.entity.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

	
	List<UserRole> findAllByRole(Role role);
	
	
	
}
