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

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class CoreValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;
	
	@ManyToOne
	@JsonBackReference
	private CompanyDna dna;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE,mappedBy = "core", orphanRemoval = true)
	@JsonManagedReference
	private Set<ScoreCore> scorecore;
	
}
