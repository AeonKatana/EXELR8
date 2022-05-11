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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private String history = "This company has no purpose yet";
	@OneToOne
	@JsonIgnore
	private Company company;
	
	@Transient
	private long companyid;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "dna")
	@JsonManagedReference
	private Set<CoreValue> corevalue;
	
	
}
