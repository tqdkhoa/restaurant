package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuItemService;
import com.example.demo.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class MenuItemController {

	public static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

	@Autowired
	MenuItemService menuItemService;

	// -------------------Retrieve All Menu Items---------------------------
	@RequestMapping(value = "/menuitem/", method = RequestMethod.GET)
	public ResponseEntity<List<MenuItem>> listAllMenuItems() {
		List<MenuItem> menuItems = menuItemService.findAllMenuItems();
		if (menuItems.isEmpty())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// -------------------Retrieve Single Menu Item--------------------------------
	@RequestMapping(value = "/menuitem/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMenuItem(@PathVariable("id") long id) {
		logger.info("Fetching MenuItem with id {}", id);
		MenuItem item = menuItemService.findById(id);
		if (item == null) {
			logger.error("MenuItem with id {} not found", id);
			return new ResponseEntity(new CustomErrorType("Menu Item with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MenuItem>(item, HttpStatus.OK);
	}

	// -------------------Create a MenuItem-------------------------------------------
	@RequestMapping(value = "/menuitem/", method = RequestMethod.POST)
	public ResponseEntity<?> createMenuItem(@RequestBody MenuItem item, UriComponentsBuilder ucBuidler) {
		logger.info("Create menu item: {}", item);

		if (menuItemService.isMenuItemExist(item)) {
			logger.error("Unable to create. A MenuItem with name {} already exist", item.getName());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A MenuItem with name" + item.getName() + " already exist"),
					HttpStatus.CONFLICT);
		}
		menuItemService.saveMenuItem(item);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuidler.path("/api/user/{id}").buildAndExpand(item.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	// ------------------- Update a MenuItem ------------------------------------------------
	@RequestMapping(value = "/menuitem/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMenuItem(@PathVariable("id") long id, @RequestBody MenuItem item) {
		logger.info("Update menu item wit id {}", id);

		MenuItem currentItem = menuItemService.findById(id);
		if (currentItem == null) {
			logger.error("Unable to update. A Menu Item with id {} not found", id);
			return new ResponseEntity(new CustomErrorType("Unable to update. A Menu Item with id" + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		currentItem.setName(item.getName());
		currentItem.setDescription(item.getDescription());
		currentItem.setImageUrl(item.getImageUrl());
		currentItem.setPrice(item.getPrice());
		currentItem.setDetails(item.getDetails());

		menuItemService.saveMenuItem(currentItem);

		return new ResponseEntity<MenuItem>(currentItem, HttpStatus.OK);
	}
	
	// ------------------- Delete a User-----------------------------------------
	@RequestMapping(value = "/menuitem/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMenuItem(@PathVariable("id") long id){
		logger.info("Delete menu item with id {}", id);
		
		MenuItem item = menuItemService.findById(id);
		if(item == null) {
			logger.error("Unable to delete. A menu item with id {} not found", id);
			return new ResponseEntity("Unable to delete.A menu item with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		
		menuItemService.deleteMenuItemById(id);
		return new ResponseEntity<MenuItem>(HttpStatus.NO_CONTENT);
	}
	
	// ------------------- Delete all MenuItem-------------	----------------------------
	@RequestMapping(value = "/menuitem/", method = RequestMethod.DELETE)
	public ResponseEntity<MenuItem> deleteMenuItem(){
		logger.info("Delete All Menu Items");
		menuItemService.deleteAllMenuItems();
		return new ResponseEntity<MenuItem>(HttpStatus.NO_CONTENT);
	}
	
}
