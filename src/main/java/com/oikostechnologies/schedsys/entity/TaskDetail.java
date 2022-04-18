package com.oikostechnologies.schedsys.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TaskDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String description;
	private LocalDateTime sdate;
	private LocalDateTime edate;
	private String status;
	private boolean recurring;
	private LocalDate until;
	private String recurringday;
	private boolean done;
	
	@OneToMany(mappedBy = "taskdetail")
	@JsonManagedReference
	private Set<UserTask> taskdetail;
	
	
	@ManyToOne
	@JsonBackReference
	private Task task;
}
