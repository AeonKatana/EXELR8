package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.RegistrationToken;

public interface RegistrationTokenRepo extends JpaRepository<RegistrationToken, Long> {

	RegistrationToken findByToken(String token);

}
