package com.oikostechnologies.schedsys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScoreCore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String indicator;
	
	@ManyToOne
	@JsonBackReference
	private Scorecard card;
	
	@ManyToOne
	@JsonBackReference
	private CoreValue core;
	
}
