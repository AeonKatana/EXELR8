package com.oikostechnologies.schedsys.datatable;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class , basePackages = "com.oikostechnologies.schedsys.datatable.repo")
public class DatatablesConfig {

}

