package com.oikostechnologies.schedsys.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String deptname;
	
	
	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE,orphanRemoval = true)
	@JsonIgnore
	private Set<DailyTask> dailies;
	
	
	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@OrderBy("id ASC")
	private Set<UserDepartment> userdepartment;
	
	@ManyToOne
	@JsonBackReference
	private Company company;
	@Transient
	@JsonSerialize
	public String companyname() { 
		try {
			return company.getCompname();
		}catch(Exception e) {
			return "Non-Affiliated";
		}
	}
	@Transient
	@JsonSerialize
	public String companycolor() {
		try {
			return company.getColor();
		}catch(Exception e) {
			return "black";
		}
	}
	@Transient
	public long usercount()
	{
		return userdepartment.size();
	}
	
	@Transient
	public List<UserDepartment> supervisor() {
		
		return userdepartment.stream().filter(x -> x.getDeptrole().equals("SUPERVISOR")).collect(Collectors.toList());
		
	}
}
