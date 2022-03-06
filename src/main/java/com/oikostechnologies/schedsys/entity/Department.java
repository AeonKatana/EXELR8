package com.oikostechnologies.schedsys.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String deptname;
	
	@OneToMany(mappedBy = "department")
	private Set<Task> tasks;
	
	@OneToMany(mappedBy = "department")
	private Set<UserDepartment> userdepartment;
	
	@ManyToOne
	private Company company;
	
	
	
}
