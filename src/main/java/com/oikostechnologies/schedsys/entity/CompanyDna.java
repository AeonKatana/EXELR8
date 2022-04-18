package com.oikostechnologies.schedsys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyDna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String history;
	private String vision;
	private String mission;
	private String philosophy;
	private String corevalue;
	
	@OneToOne
	private Company company;
	
}
