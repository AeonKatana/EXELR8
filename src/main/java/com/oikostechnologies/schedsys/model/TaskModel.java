package com.oikostechnologies.schedsys.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {

	private long id;
	private String taskname;
	private String detail;
	private LocalDate edate;
	private List<PeopleModel> who;
	private List<PeopleModel> notify;

}
