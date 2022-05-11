package com.oikostechnologies.schedsys;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oikostechnologies.schedsys.entity.Role;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserRole;
import com.oikostechnologies.schedsys.leadprojection.LeadDTO;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.RoleRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.repo.UserRoleRepo;

@SpringBootTest
class SchedSysApplicationTests {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	void createRoles() {
		
		
		Role master = Role.builder()
				   .rolename("MASTERADMIN")
				   .build();
		Role executive = Role.builder()
				   .rolename("EXECUTIVE")
				   .build();
		Role projlead = Role.builder()
				   .rolename("PROJLEAD")
				   .build();
		Role associate = Role.builder()
				   .rolename("ASSOCIATE")
				   .build();
		Role manager = Role.builder()
				   .rolename("MANAGER")
				   .build();
		Role supervisor = Role.builder()
				   .rolename("SUPERVISOR")
				   .build();
		
		roleRepo.save(master);
		roleRepo.save(executive);
		roleRepo.save(manager);
		roleRepo.save(projlead);
		roleRepo.save(supervisor);
		roleRepo.save(associate);
		
	}
	
	
	void createSuperadmin() {
		
		Role role = Role.builder()
				   .rolename("SUPERADMIN")
				   .build();
		
		roleRepo.save(role);
		
		User user = User.builder()
				  .firstname("Rean")
				  .lastname("Schwarzer")
				  .password(encoder.encode("12345").toCharArray())
				  .email("rean@gmail.com")
				  .enabled(true)
				  .contactno(639564412627L)
				  .build();
		
		userRepo.save(user);
		
		
		
		UserRole ur = UserRole.builder()
				      .role(role)
				      .user(user)
				      .build();
		
		userRoleRepo.save(ur);
	}
	
	@Test
	void leaderboard() {
		
		for(LeadDTO c : companyRepo.leaderboard(5, 2022)) {
			System.out.println("Company : " + c.getCompname());
			System.out.println("Done :" + c.getDailydone());
		}
		
	}
	
}