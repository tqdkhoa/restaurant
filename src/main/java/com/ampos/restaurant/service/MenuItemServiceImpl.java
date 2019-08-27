package com.ampos.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.repositories.MenuItemRepository;

@Service("menuItemService")
@Transactional
public class MenuItemServiceImpl implements MenuItemService{

	@Autowired
	private MenuItemRepository repository;
	
	public MenuItem findById(Long id) {
		return repository.findById(id).orElse(null);
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
	
	public void deleteMenuItemById(Long id) {
		repository.deleteById(id);
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
