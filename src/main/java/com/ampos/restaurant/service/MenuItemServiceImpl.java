package com.ampos.restaurant.service;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.MenuItemDTO;
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
	
	public List<MenuItem> findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(String name, Pageable pageable){
		return repository.findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(name.toLowerCase(), pageable);
	}
	
	public void saveMenuItem(MenuItem item) {
		repository.save(item);
	}
	
	public void deleteMenuItemById(Long id) {
		repository.deleteById(id);
	}
	
	public List<MenuItem> findAllMenuItems(Pageable pageable){
		return repository.findAll(pageable).getContent();
	}
	
	public boolean isMenuItemExist(MenuItem item) {
		return repository.countByName(item.getName()) != 0;
	}
	
	public List<MenuItem> findMenuByInIds(Collection<Long> ids){
		return repository.findMenuByInIds(ids);
	}
	
	public List<MenuItem> findMenuByInNames(Collection<String> names){
		return repository.findMenuByInNames(names);
	}
}
