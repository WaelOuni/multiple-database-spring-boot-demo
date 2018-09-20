package com.example.demo.controller.postgres;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.postgres.Item;
import com.example.demo.repo.postgres.ItemRepository;

@RestController
@RequestMapping("/api")
public class ItemController {

	@Autowired
	ItemRepository repository;

	@GetMapping("/items")
	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<>();
		repository.findAll().forEach(items::add);
		System.out.println("count : " + repository.count());
		return items;
	}

	@GetMapping("/item")
	public Item getItem() {
		return repository.findByName("wael");
	}

	@GetMapping("/item/create")
	public Item postCustomer() {
		Item _item = repository.save(new Item("benJaafer"));
		return _item;
	}
}
