package com.example.demo.repo.postgres;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.postgres.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
	Item findByName(String name);
}