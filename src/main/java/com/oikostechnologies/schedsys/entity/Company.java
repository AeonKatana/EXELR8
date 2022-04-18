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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
	
	@OneToMany(mappedBy = "company")
	@JsonManagedReference
	private Set<Department> departments;
	
	@OneToMany(mappedBy = "company" , fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	@JsonIgnoreProperties("company")
	private Set<User> user;
	
	@OneToOne(mappedBy = "company")
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
		masteradmin = user.stream().filter(x -> x.role().equalsIgnoreCase("MASTERADMIN")).findFirst().get().fullname();
		return masteradmin;
	}
	
}
