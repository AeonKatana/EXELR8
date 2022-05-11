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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Scorecard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String mainscorecard;
	private String perforaccel;
	private String educationalbg;
	private String metrics;
	private String corecompetencies;
	private String definition;
	private String indicators;
	
	@OneToOne
	@JsonIgnoreProperties("scorecard")
	private User user;
	
	@OneToMany(mappedBy = "card",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private Set<ScoreCore> scorecore;
}
