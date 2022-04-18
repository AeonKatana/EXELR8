package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.PasswordToken;

public interface PasswordTokenRepo extends JpaRepository<PasswordToken, Long> {

	PasswordToken findByToken(String token);

}
