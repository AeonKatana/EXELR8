package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.ActivityLog;
import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.User;

public interface ActlogRepo extends JpaRepository<ActivityLog, Long> {

	
	List<ActivityLog> findAllByOrderByDateDesc();
	List<ActivityLog> findByUserOrderByDateDesc(User user);
	
	@Query("Select a from ActivityLog a join a.user u join u.company c where c =:company order by date desc")
	Page<ActivityLog> findAllByCompanyOrderByDateDesc(@Param("company") Company company, Pageable page);
	
	Page<ActivityLog> findAllByOrderByDateDesc(Pageable page);
	Page<ActivityLog> findByUserOrderByDateDesc(User user, Pageable page);
}
