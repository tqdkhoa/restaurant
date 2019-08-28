package com.ampos.restaurant.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.MenuItemDTO;

public interface MenuItemService {

	MenuItem findById(Long Id);

	MenuItem findByName(String name);

	List<MenuItem> findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(String name, Pageable pageable);

	void saveMenuItem(MenuItem item);

	void deleteMenuItemById(Long id);

	List<MenuItem> findAllMenuItems(Pageable pageable);

	boolean isMenuItemExist(MenuItem item);
	
	List<MenuItem> findMenuByInIds(Collection<Long> ids);
	
	List<MenuItem> findMenuByInNames(Collection<String> names);

}
