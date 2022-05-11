package com.oikostechnologies.schedsys.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DailyTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private LocalDateTime starteddate;
	private String description;
	@Default
	private String note = "No Notes";
	private LocalDate until;
	private boolean recurring;
	private boolean done;
	private boolean deleted;
	
	@OneToMany(mappedBy = "daily", fetch =FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<NotifyUser> notifyusers;  
	
	@ManyToOne
	@JsonIgnoreProperties("dailies")
	private User user;
	
	@ManyToOne
	@JsonIgnoreProperties("dailies")
	private Department department;
	
	@ManyToOne
	@JsonIgnore
	private User assignedby;
}
