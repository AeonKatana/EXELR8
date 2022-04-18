package com.oikostechnologies.schedsys.datatable;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.oikostechnologies.schedsys.repo")
public class DefaultJpaConfig {

}
