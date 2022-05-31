package com.oikostechnologies.schedsys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserDepartment {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JsonIgnore
	private Department department;
	
	private String deptrole;
	
	@Transient
	public String username() {
		return user.getFirstname();
	}
	@Transient
	public String departmentname() {
		return department.getDeptname();
	}
}
