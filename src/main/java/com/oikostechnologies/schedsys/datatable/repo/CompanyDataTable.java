package com.oikostechnologies.schedsys.datatable.repo;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.oikostechnologies.schedsys.entity.Company;

public interface CompanyDataTable extends DataTablesRepository<Company, Long> {

}
