package com.oikostechnologies.schedsys.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeptModel {

	
	private String deptname;
	private List<PeopleModel> people;
	
}
