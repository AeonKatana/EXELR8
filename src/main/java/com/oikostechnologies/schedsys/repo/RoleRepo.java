package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	
	Role findByRolename(String rolename);
}
