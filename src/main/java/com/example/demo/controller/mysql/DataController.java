package com.example.demo.controller.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.mysql.Data;
import com.example.demo.repo.mysql.DataRepository;

@RestController
@RequestMapping("/api")
public class DataController {

	@Autowired
	DataRepository repository;

	@GetMapping("/datas")
	public List<Data> getAllItems() {
		List<Data> datas = new ArrayList<>();
		repository.findAll().forEach(datas::add);
		System.out.println("count : " + repository.count());
		return datas;
	}

}
