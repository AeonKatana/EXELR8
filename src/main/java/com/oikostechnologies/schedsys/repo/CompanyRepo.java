package com.oikostechnologies.schedsys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.leadprojection.LeadDTO;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

	public Page<Company> findByCompnameContaining(String search ,Pageable page);
	
	Company findByUser(User user);
	
	Company findByCompname(String name);
	
	
	@Query("Select c.compname as compname, count(case when dt.done = true and MONTH(dt.starteddate) =:month and YEAR(dt.starteddate) =:year then 1 end) as dailydone, c.color as color from Company c left join c.user u left join u.dailies dt group by c.compname")
	List<LeadDTO> leaderboard(@Param("month") int month , @Param("year") int year);
	
	@Query("Select c.compname as compname, count(case when dt.done = false and dt.until <:today then 1 end) as dailydone, c.color as color from Company c left join c.user u left join u.dailies dt group by c.compname")
	List<LeadDTO> overlead(@Param("today") LocalDate today);
	
}
