package com.oikostechnologies.schedsys.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Notification;
import com.oikostechnologies.schedsys.entity.User;

public interface NotificationRepo extends JpaRepository<Notification, Long>{

	Page<Notification> findAllByUser(User user, Pageable page); 
	
}
