package com.oikostechnologies.schedsys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.User;

public interface DailyTaskRepo extends JpaRepository<DailyTask, Long> {

	List<DailyTask> findAllByUserAndDoneFalseOrderByStarteddateDesc(User user);
	
	List<DailyTask> findAllByDoneFalseOrderByStarteddateDesc();
	
	List<DailyTask> findAllByDoneFalseAndUserOrderById(User user);
	
	@Query("SELECT count(*) from DailyTask dt where dt.starteddate = :today")
	long countDailyToday(@Param("today") LocalDate today);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u join u.company c where c.compname =:company and dt.starteddate =:today")
	long countDailyToday(@Param("today") LocalDate today, @Param("company") String company);
	
	@Query("SELECT count(*) from DailyTask dt join dt.user u where dt.starteddate = :today and u.id =:id and dt.done = false")
	long countDailyToday(@Param("today") LocalDate today, @Param("id") long id);
	
	@Query("SELECT count(*) from DailyTask dt where dt.until < :today and dt.done = false")
	long countOverdue(@Param("today") LocalDate today);
	
	@Query("SELECT dt from DailyTask dt join dt.user u left join u.company c where (u.firstname like %:search% OR u.lastname like %:search% OR dt.title like %:search% OR"
			+ " dt.description like %:search% OR c.compname like %:search%) and dt.done = false order by dt.title asc")
	List<DailyTask> searchTask(@Param("search") String search);
	
	
	
	
}
