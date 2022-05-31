package com.oikostechnologies.schedsys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreValueModel {

	private long id;
	private String title;
	private String description;
	private String indicator;
	private long companyid;
}
