package com.oikostechnologies.schedsys.entity.view;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "quickview")
public class Quickview {

	
	private long id;
	private long deptid;
	@Id
	private long taskid;
	private String firstname;
	private String compname;
	private String deptname;
	private String taskname;
	private LocalDate sdate;
	private LocalDate edate;
	private int totaltask;
	private int completed;
	
	
	
}
