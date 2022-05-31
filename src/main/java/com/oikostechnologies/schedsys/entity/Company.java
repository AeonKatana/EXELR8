package com.oikostechnologies.schedsys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

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
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String compname;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private Set<Department> departments;
	
	
	@OneToMany(mappedBy = "company" , fetch = FetchType.EAGER , cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private Set<User> user;
	
	@OneToOne(mappedBy = "company", cascade = CascadeType.REMOVE)
	private CompanyDna dna;
	
	private String color;
	
	@Transient
	public String companycolor() {
		try {
			return this.color;
		}catch(Exception e) {
			return "black";
		}
	}
	
	
	@Transient
	public int usercount() {
		return user.size();
	}
	@Transient
	public int deptcount() {
		return departments.size();
	}
	
	@Transient
	@JsonSerialize
	private String masteradmin;
	
	@Transient
	@JsonSerialize
	public String masteradmin() {
		
		if(user.stream().filter(x -> x.role().equalsIgnoreCase("MASTERADMIN")).findFirst().get() == null) {
		     return "No MASTERADMIN";
		}
		 masteradmin = user.stream().filter(x -> x.role().equalsIgnoreCase("MASTERADMIN")).findFirst().get().fullname();
		if(masteradmin == null || masteradmin.isEmpty()) {
			return "No MASTERADMIN";
		}
		return masteradmin;
	}
	
	@Transient
	@JsonSerialize
	public int donetask() {
		int total = 0;
		for(User u : user) {
			total += u.getDailies().stream().filter(x -> x.isDone() == true).count();
		}
		return total;
	}
	
}
