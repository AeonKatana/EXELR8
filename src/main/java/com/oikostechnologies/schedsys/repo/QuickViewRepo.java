package com.oikostechnologies.schedsys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.view.Quickview;

public interface QuickViewRepo extends JpaRepository<Quickview, Long> {

	List<Quickview> findAllByDeptidIsNull();
	List<Quickview> findAllByCompname(String compname);
	
}
