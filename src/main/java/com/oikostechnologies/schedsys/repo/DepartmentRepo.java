package com.oikostechnologies.schedsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oikostechnologies.schedsys.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

}
