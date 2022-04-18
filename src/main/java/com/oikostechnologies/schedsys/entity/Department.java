package com.oikostechnologies.schedsys.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@JsonManagedReference
	private Set<Task> tasks;
	
	@OneToMany(mappedBy = "department")
	@JsonManagedReference
	private Set<UserDepartment> userdepartment;
	
	@ManyToOne
	@JsonBackReference
	private Company company;
	
	@Transient
	public String companyname() { 
		try {
			return company.getCompname();
		}catch(Exception e) {
			return "Non-Affiliated";
		}
	}
	@Transient
	public long usercount()
	{
		return userdepartment.size();
	}
}
