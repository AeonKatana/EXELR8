package com.oikostechnologies.schedsys.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private LocalDate starteddate;
	private String description;
	@Default
	private String note = "No Notes";
	private LocalDate until;
	private boolean recurring;
	private boolean done;
	
	@OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<NotifyUser> notifyusers;  
	
	@ManyToOne
	@JsonBackReference
	private User user;
	
	@ManyToOne
	@JsonBackReference
	private User assignedby;
}
