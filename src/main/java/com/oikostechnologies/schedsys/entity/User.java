package com.oikostechnologies.schedsys.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	
	
	@OneToMany(mappedBy = "user")
	private Set<UserRole> userrole;
	
	@ManyToOne
	private Company company;

	
	@OneToMany(mappedBy = "user")
	private Set<UserDepartment> userdepartment;
	
	
	@OneToMany(mappedBy = "user")
	private Set<UserTask> tasks;
	
	
	@Transient
	public String role() {
		return userrole.stream().findFirst().get().getRole().getRolename();
	}
	
	@Transient
	public String companyname() { 
		try {
			return company.getCompname();
		}catch(Exception e) {
			return "No Company";
		}
	}
	
	@Transient
	public String fullname() {
		return firstname + " " + lastname;
	}
	
	@Transient
	public int companysize() {
		
		try {
			return company.usercount();
		}catch(Exception e) {
			return 0;
		}
		
	}
	
}
