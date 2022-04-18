package com.oikostechnologies.schedsys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstname;
	private String lastname;
	private String email;
	private char[] password;
	private boolean enabled;
	private long contactno;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	@JsonManagedReference
	private Set<ActivityLog> actlog;
	
	
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	@JsonManagedReference
	private Set<UserRole> userrole;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JsonIgnoreProperties("user")
	private Company company;

	
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonManagedReference
	private Set<UserDepartment> userdepartment;
	
	
	@OneToMany(mappedBy = "user" , fetch =  FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonManagedReference
	private Set<UserTask> tasks;
	
	
	@OneToMany(mappedBy = "user" , fetch=FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonManagedReference
	private Set<DailyTask> dailies;
	
	@OneToOne(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonIgnore
	private RegistrationToken token;
	
	@OneToOne(mappedBy ="user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonIgnore
	private PasswordToken passtoken;
	
	@OneToMany(mappedBy = "assignedby" , fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<DailyTask> assigned;
	
	@Transient
	private String role;
	@Transient
	private String companyname;
	@Transient
	private String fullname;
	@Transient
	private int companysize;
	@Transient
	private String companycolor;
	
	@Transient
	@JsonSerialize
	public String role() {
		role = userrole.stream().findFirst().get().getRole().getRolename();
		return role;
	}
	
	@Transient
	@JsonSerialize
	public String companyname() { 
		try {
			companyname = company.getCompname();
			return companyname;
		}catch(Exception e) {
			companyname = "Does not belong to a company";
			return companyname;
		}
	}
	
	@Transient
	@JsonSerialize
	public String fullname() {
		fullname = firstname + " " + lastname;
		return fullname;
	}
	
	@Transient
	@JsonSerialize
	public int companysize() {
		
		try {
			companysize = company.usercount();
			return companysize;
		}catch(Exception e) {
			return companysize = 0;
		}
		
	}
	@Transient
	@JsonSerialize
	public String companycolor() {
		try {
			return companycolor = company.getColor();
		}catch(Exception e) {
			return companycolor =  "black";
		}
	}
	
	
	
}
