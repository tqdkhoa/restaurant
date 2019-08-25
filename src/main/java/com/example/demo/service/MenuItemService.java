package com.example.demo.service;

import java.util.List;

import com.example.demo.model.MenuItem;

public interface MenuItemService {

	MenuItem findById(Long Id);

	MenuItem findByName(String name);
	
	void saveMenuItem(MenuItem item);
	
	void updateMenuItem(MenuItem item);
	
	void deleteMenuItemById(Long Id);
	
	void deleteAllMenuItems();
	
	List<MenuItem> findAllMenuItems();
	
	boolean isMenuItemExist(MenuItem item);
}
