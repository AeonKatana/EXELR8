package com.oikostechnologies.schedsys.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class QuickViewModel {

	private String manager;
	private String department;
	private String task;
	private LocalDateTime edate;
	private LocalDateTime sdate;
	private String progress;
	
	
}
