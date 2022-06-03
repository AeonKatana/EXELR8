package com.oikostechnologies.schedsys.repo;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.leadprojection.LeadUserDTO;

public interface UserRepo extends JpaRepository<User, Long> {

	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = :role")
	Page<User> getUsersByRole(@Param("role")String role, Pageable pageable);
		
	@Query("Select u from User u join u.userrole ur join ur.role r order by :param")
	List<User> getAllByOrderBy(@Param("param") String param);
	
	User findByEmail(String email);
	
	@Query("Select concat(u.firstname,' ',u.lastname) as fullname, count(u.violationcount) as dailydone"
			+ ", c.color as color, c.compname as compname from User u left join u.company c left join u.dailies dt group by u.firstname order by count(dt.done) desc")
	List<LeadUserDTO> tardylead();
	
	@Query("Select u from User u where not exists(select dt from u.dailies dt where DATE(dt.starteddate) =:today)")
	List<User> getAllTardyUsers(@Param("today") Date today);
	
	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = 'PROJLEAD'")
	List<User> getAllProjectLeader();
	
	@Query("Select u from User u join u.company c join u.userrole ur join ur.role r where r.rolename = 'PROJLEAD' and c.compname =:companyname")
	List<User> getAllProjectLeaderByCompany(@Param("companyname")String companyname);
	
	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = 'ASSOCIATE'")
	List<User> getAllAssociate();
	
	@Query("Select u from User u join u.company c join u.userrole ur join ur.role r where r.rolename = 'ASSOCIATE' and c.compname =:companyname")
	List<User> getAllAssociateByCompany(@Param("companyname")String companyname);
	
	
	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = 'SUPERVISOR'")
	List<User> getAllSupervisor();
	
	@Query("Select u from User u join u.company c join u.userrole ur join ur.role r where r.rolename = 'SUPERVISOR' and c.compname =:companyname")
	List<User> getAllSupervisorByCompany(@Param("companyname")String companyname);
	
	
	Page<User> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname , Pageable page);
	
	Page<User> findAllByFirstnameContaining(String firstname ,Pageable page);
	
	@Query("Select u from User u join u.company c where c.compname =:compname")
	List<User> getAllByCompanyname(@Param("compname")String compname);
	
	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = 'SUPERADMIN'")
	User findSuperAdmin();
	
	@Query("Select u from User u join u.dailies dt where dt.until <:today and dt.done = false")
	List<User> getAllUserOverdue(@Param("today") LocalDate today);
	
	@Query("Select concat(u.firstname,' ',u.lastname) as fullname, count(case when dt.done = true and MONTH(dt.starteddate) =:month and YEAR(dt.starteddate) =:year then 1 end) as dailydone"
			+ ", c.color as color, c.compname as compname from User u left join u.company c left join u.dailies dt where c =:company group by u.firstname order by count(dt.done) desc")
	List<LeadUserDTO> leaderboard(@Param("month") int month, @Param("year") int year, @Param("company") Company company);
	
	@Query("Select concat(u.firstname,' ',u.lastname) as fullname, count(case when dt.done = true and MONTH(dt.starteddate) =:month and YEAR(dt.starteddate) =:year then 1 end) as dailydone"
			+ ", c.color as color, c.compname as compname from User u left join u.company c left join u.dailies dt group by u.firstname order by count(dt.done) desc")
	List<LeadUserDTO> leaderboard(@Param("month") int month, @Param("year") int year);
	
	@Query("Select concat(u.firstname,' ',u.lastname) as fullname, count(case when dt.done = false and dt.until <:today then 1 end) as dailydone"
			+ ", c.color as color, c.compname as compname from User u left join u.company c left join u.dailies dt group by u.firstname order by count(dt.done) desc")
	List<LeadUserDTO> overleaderboard(@Param("today") LocalDate today);
	
	@Query("Select concat(u.firstname,' ',u.lastname) as fullname, count(case when dt.done = false and dt.until <:today then 1 end) as dailydone"
			+ ", c.color as color, c.compname as compname from User u left join u.company c left join u.dailies dt where c =:company group by u.firstname order by count(dt.done) desc")
	List<LeadUserDTO> overleaderboard(@Param("today") LocalDate today, @Param("company") Company company);
	
	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename != 'SUPERADMIN'")
	List<User> getAllExceptSuperadmin();
}

