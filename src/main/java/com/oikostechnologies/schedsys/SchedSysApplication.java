package com.oikostechnologies.schedsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SchedSysApplication{

	public static void main(String[] args) {
		SpringApplication.run(SchedSysApplication.class, args);
	}

}
