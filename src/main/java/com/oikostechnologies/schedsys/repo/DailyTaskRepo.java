package com.oikostechnologies.schedsys.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.User;

public interface DailyTaskRepo extends JpaRepository<DailyTask, Long> {

	List<DailyTask> findAllByUserAndDoneFalseOrderByStarteddateDesc(User user);
	
	List<DailyTask> findAllByDoneFalseOrderByIdDesc();
	
	List<DailyTask> findAllByDoneFalseAndUserOrderById(User user);
	
	@Query("Select dt from DailyTask dt where dt.until < :today and dt.done = false order by dt.until desc")
	List<DailyTask> getAllByDoneFalseOrderByUntilDesc(@Param("today") LocalDate today);
	
	@Query("Select dt from DailyTask dt join dt.user u join u.company c where dt.until < :today and dt.done = false and c.compname =:compname order by dt.until desc")
	List<DailyTask> getAllByDoneFalseAndCompanyOrderByUntilDesc(@Param("today")LocalDate today, @Param("compname") String companyname);
	
	@Query("Select dt from DailyTask dt join dt.user u where dt.until < :today and dt.done = false and u.id =:id order by dt.until desc")
	List<DailyTask> getAllByDoneFalseAndUserOrderByUntilDesc(@Param("today")LocalDate today, @Param("id") long id);
	
	@Query("SELECT dt from DailyTask dt join dt.user u where DATE(dt.starteddate) = :today and u.id =:id and dt.done = false")
	List<DailyTask> getAllDailyByUser(@Param("today") Date today,@Param("id") long id);
	
	@Query("SELECT dt from DailyTask dt join dt.user u join u.company c where c.compname =:company and DATE(dt.starteddate) =:today and dt.done = false")
	List<DailyTask> getAllDailyByCompany(@Param("today") Date today,@Param("company") String company);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u join dt.assignedby au where u.id =:id and au.id != u.id and dt.done = false")
	long countDailyAssignedToMeBySomeoneElse(@Param("id") long id);
	
	@Query("SELECT count(*) from DailyTask dt where DATE(dt.starteddate) = :today")
	long countDailyToday(@Param("today") Date today);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u join u.company c where c.compname =:company and DATE(dt.starteddate) =:today and dt.done = false")
	long countDailyToday(@Param("today") Date today, @Param("company") String company);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u where DATE(dt.starteddate) = :today and u.id =:id and dt.done = false")
	long countDailyToday(@Param("today") Date today, @Param("id") long id);
	
	@Query("SELECT count(*) from DailyTask dt where dt.until < :today and dt.done = false")
	long countOverdue(@Param("today") LocalDate today);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u where dt.until < :today and u.id = :id and dt.done = false")
	long countOverdueByUser(@Param("today") LocalDate today, @Param("id") long id);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u join u.company c where c.compname =:company and dt.until < :today and dt.done = false")
	long countOverdueByCompany(@Param("today") LocalDate today,@Param("company") String company);
	
	@Query("SELECT dt from DailyTask dt join dt.user u left join u.company c where (u.firstname like %:search% OR u.lastname like %:search% OR dt.title like %:search% OR"
			+ " dt.description like %:search% OR c.compname like %:search%) and dt.done = false order by dt.title asc")
	List<DailyTask> searchTask(@Param("search") String search);
	
	@Query("SELECT count(dt.id) from DailyTask dt join dt.user u join u.userdepartment ud join ud.department d where d.deptname =:department"
			+ " and dt.done = false")
	long countDailyByDepartment(@Param("department") String department);
	
	@Query("SELECT count(dt.id) from DailyTask dt join dt.user u join u.userdepartment ud join ud.department d where d.deptname =:department"
			+ " and dt.done = false and dt.until < :today")
	long countOverdueByDepartment(@Param("department") String department, @Param("today") LocalDate today);
	
	
	
}
