package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.MenuItem;

public interface MenuItemService {

	MenuItem findById(Long Id);

	MenuItem findByName(String name);

	List<MenuItem> findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(String name, String description,
			String details, Pageable pageable);

	void saveMenuItem(MenuItem item);

	void updateMenuItem(MenuItem item);

	void deleteMenuItemById(Long Id);

	void deleteAllMenuItems();

	List<MenuItem> findAllMenuItems(Pageable pageable);

	boolean isMenuItemExist(MenuItem item);
}
