package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.NotifyUser;

public interface NotifyUserRepo extends JpaRepository<NotifyUser, Long>{

	void deleteByDaily(DailyTask daily);
	void deleteAllByDaily(DailyTask daily);
}
