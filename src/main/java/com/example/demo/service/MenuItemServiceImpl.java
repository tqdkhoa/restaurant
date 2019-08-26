package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.MenuItem;
import com.example.demo.repositories.MenuItemRepository;

@Service("menuItemService")
@Transactional
public class MenuItemServiceImpl implements MenuItemService{

	@Autowired
	private MenuItemRepository repository;
	
	public MenuItem findById(Long Id) {
		return repository.findById(Id).orElse(null);
	}
	
	public MenuItem findByName(String name) {
		return repository.findByName(name);
	}
	
	public List<MenuItem> findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(String name, String description,
			String details, Pageable pageable){
		return repository.findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(name, description, details, pageable);
	}
	
	public void saveMenuItem(MenuItem item) {
		repository.save(item);
	}
	
	public void updateMenuItem(MenuItem item) {
		saveMenuItem(item);
	}
	
	public void deleteMenuItemById(Long Id) {
		repository.deleteById(Id);
	}
	
	public void deleteAllMenuItems() {
		repository.deleteAll();
	}
	
	public List<MenuItem> findAllMenuItems(Pageable pageable){
		return repository.findAll(pageable).getContent();
	}
	
	public boolean isMenuItemExist(MenuItem item) {
		return findByName(item.getName()) != null;
	}
}
