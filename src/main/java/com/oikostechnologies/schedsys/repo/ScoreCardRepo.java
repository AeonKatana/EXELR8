package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Scorecard;
import com.oikostechnologies.schedsys.entity.User;

public interface ScoreCardRepo extends JpaRepository<Scorecard, Long> {

	Scorecard findByUser(User user);
	
}
