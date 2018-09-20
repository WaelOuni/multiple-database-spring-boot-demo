package com.example.demo.repo.mysql;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.mysql.Data;

public interface DataRepository extends CrudRepository<Data, Long> {
	Data findByTitle(String title);
}