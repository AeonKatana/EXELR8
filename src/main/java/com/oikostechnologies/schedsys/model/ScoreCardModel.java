package com.oikostechnologies.schedsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCardModel {

	private long id;
	private String mainscorecard;
	private String perforaccel;
	private String educationalbg;
	private String metrics;
	private String corecompetencies;
	private String definition;
	private String indicators;
	private String customer;
	
	private long userid;
	
	private String role;
	private String roledesc;
	
}
