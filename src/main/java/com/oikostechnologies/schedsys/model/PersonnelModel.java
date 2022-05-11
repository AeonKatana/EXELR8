package com.oikostechnologies.schedsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelModel {

	private String firstname;
	private String lastname;
	private String role;
	private String email;
	private long contactno;
	private String companyname;
	
}
