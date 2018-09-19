package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
	Item findByName(String name);
}