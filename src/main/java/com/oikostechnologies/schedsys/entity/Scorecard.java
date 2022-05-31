package com.oikostechnologies.schedsys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String customer;
	private String roledesc;
	
	@OneToOne
	@JsonIgnore
	private User user;
}
