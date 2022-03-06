package com.oikostechnologies.schedsys.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String compname;
	
	@OneToMany(mappedBy = "company")
	private Set<Department> departments;
	
	@OneToMany(mappedBy = "company")
	private Set<User> user;
	
	
	@Transient
	public int usercount() {
		
		return user.size();
	}
	@Transient
	public int deptcount() {
		return departments.size();
	}
	
	
}
