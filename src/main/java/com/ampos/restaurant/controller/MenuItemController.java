package com.ampos.restaurant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ampos.restaurant.exception.ObjectAlreadyExistException;
import com.ampos.restaurant.exception.ObjectNotFoundException;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.service.MenuItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/menu-items")
@Api(value = "Menu Item Management", description = "Menu Item Management")
public class MenuItemController {

	private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

	@Autowired
	private MenuItemService menuItemService;

	// -------------------Retrieve All Menu Items---------------------------
	@ApiOperation(value = "Retrieve All Menu Items")
	@GetMapping
	public List<MenuItem> listAllMenuItems(Pageable pageable) {
		return menuItemService.findAllMenuItems(pageable);
	}
	

	// -------------------Retrieve Single Menu Item--------------------------------
	@ApiOperation(value = "Retrieve Single Menu Item")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getMenuItem(
			@ApiParam(value = "Menu id from which Menu object will retrieve", required = true)
			@PathVariable("id") long id) {
		
		logger.info("Fetching MenuItem with id {}", id);
		MenuItem item = menuItemService.findById(id);
		if (item == null) {
			logger.error("MenuItem with id {} not found", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		return new ResponseEntity<MenuItem>(item, HttpStatus.OK);
	}
	

	// --------------Retrieve Menu Item(s) by Name, Description, and Details----------
	@ApiOperation(value = "Retrieve Menu Item(s) by Name, Description, and Details")
	@GetMapping(value = "/search")
	public ResponseEntity<?> searchMenuByNameOrDescriptinOrDetails(
			@ApiParam(value = "Search keyword (title, description, or detail) of Menu object will retrieve", required = true)
			@RequestParam(value = "name") String name, Pageable pageable) {
		List<MenuItem> menuItems = menuItemService.findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(name, pageable);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}
	

	// -------------------Create a MenuItem-------------------------
	@ApiOperation(value = "Create a Menu Item")
	@PostMapping
	public ResponseEntity<?> createMenuItem(
			@ApiParam(value = "A menu object, include: title, imageUrl, description, price and detail to create a new menu", required = true)
			@RequestBody MenuItem item) {
		
		logger.info("Create menu item: {}", item);
		if (menuItemService.isMenuItemExist(item)) {
			logger.error("Unable to create. A MenuItem with name {} already exist", item.getName());
			throw new ObjectAlreadyExistException(item.getName());
		}
		menuItemService.saveMenuItem(item);
		return new ResponseEntity<MenuItem>(item, HttpStatus.CREATED);
	}
	

	// ------------------- Update a MenuItem ------------------------------
	@ApiOperation(value = "Update a MenuItem")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMenuItem(
			@ApiParam(value = "Menu id from which Menu object will retrieve", required = true)
			@PathVariable("id") long id,
			@ApiParam(value = "A menu object, include: title, imageUrl, description, price and detail to update a new menu", required = true)
			@RequestBody MenuItem item) {
		
		logger.info("Update menu item wit id {}", id);
		MenuItem currentItem = menuItemService.findById(id);
		if (currentItem == null) {
			logger.error("Unable to update. A Menu Item with id {} not found", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}

		currentItem.setName(item.getName());
		currentItem.setDescription(item.getDescription());
		currentItem.setImageUrl(item.getImageUrl());
		currentItem.setPrice(item.getPrice());
		currentItem.setDetails(item.getDetails());
		menuItemService.saveMenuItem(currentItem);
		return new ResponseEntity<MenuItem>(currentItem, HttpStatus.OK);
	}

	// ------------------- Delete a Menu Item-------------------------------------
	@ApiOperation(value = "Delete a Menu Item")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteMenuItem(
			@ApiParam(value = "Menu id from which Menu object will be deleted", required = true)
			@PathVariable("id") long id) {
		
		logger.info("Delete menu item with id {}", id);
		MenuItem item = menuItemService.findById(id);
		if (item == null) {
			logger.error("Unable to delete. A menu item with id {} not found", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		menuItemService.deleteMenuItemById(id);
		return new ResponseEntity<MenuItem>(HttpStatus.NO_CONTENT);
	}

}
