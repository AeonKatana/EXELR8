package com.oikostechnologies.schedsys.datatable.repo;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.oikostechnologies.schedsys.entity.User;

public interface UserDataTable extends DataTablesRepository<User, Long> {

}
