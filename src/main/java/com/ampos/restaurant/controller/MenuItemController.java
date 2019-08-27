package com.ampos.restaurant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ampos.restaurant.exception.ItemConflictException;
import com.ampos.restaurant.exception.ItemNotFoundException;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.service.MenuItemService;
import com.ampos.restaurant.util.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/menu-items")
@Api(value="Menu Item Management", description="Menu Item Management")
public class MenuItemController {

	public static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

	@Autowired
	MenuItemService menuItemService;

	// -------------------Retrieve All Menu Items---------------------------
	@ApiOperation(value = "Retrieve All Menu Items")
	@GetMapping
	public ResponseEntity<List<MenuItem>> listAllMenuItems(Pageable pageeable) {
		List<MenuItem> menuItems = menuItemService.findAllMenuItems(pageeable);
		if (menuItems.size() == 0)
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// -------------------Retrieve Single Menu Item--------------------------------
	@ApiOperation(value = "Retrieve Single Menu Item")
	@GetMapping(value = "{id}")
	public ResponseEntity<?> getMenuItem(@PathVariable("id") long id) {
		logger.info("Fetching MenuItem with id {}", id);
		MenuItem item = menuItemService.findById(id);
		if (item == null) {
			logger.error("MenuItem with id {} not found", id);
			throw new ItemNotFoundException(String.valueOf(id), "MenuItem with id");
		}
		return new ResponseEntity<MenuItem>(item, HttpStatus.OK);
	}

	// -------------------Retrieve Menu Item(s) by Name, Description, and Details--------------------------------
	@ApiOperation(value = "Retrieve Menu Item(s) by Name, Description, and Details")
	@GetMapping(value = "/search")
	public ResponseEntity<?> searchMenuByNameOrDescriptinOrDetails(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "details", required = false) String details, Pageable pageeable) {
		List<MenuItem> menuItems = menuItemService.findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(name,
				description, details, pageeable);
		if (menuItems.isEmpty())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// -------------------Create a MenuItem-------------------------------------------
	@ApiOperation(value = "Create a MenuItem")
	@PostMapping
	public ResponseEntity<?> createMenuItem(@RequestBody MenuItem item) {
		logger.info("Create menu item: {}", item);

		if (menuItemService.isMenuItemExist(item)) {
			logger.error("Unable to create. A MenuItem with name {} already exist", item.getName());
			throw new ItemConflictException(item.getName(), "Unable to create. A MenuItem with name");
		}
		menuItemService.saveMenuItem(item);
		return new ResponseEntity<MenuItem>(item, HttpStatus.CREATED);
	}

	// ------------------- Update a MenuItem ---------------------------------------------
	@ApiOperation(value = "Update a MenuItem")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMenuItem(@PathVariable("id") long id, @RequestBody MenuItem item) {
		logger.info("Update menu item wit id {}", id);

		MenuItem currentItem = menuItemService.findById(id);
		if (currentItem == null) {
			logger.error("Unable to update. A Menu Item with id {} not found", id);
			throw new ItemNotFoundException(String.valueOf(id), "Unable to update.A menu item with id");
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
	@ApiOperation(value = "Delete a User")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteMenuItem(@PathVariable("id") long id) {
		logger.info("Delete menu item with id {}", id);

		MenuItem item = menuItemService.findById(id);
		if (item == null) {
			logger.error("Unable to delete. A menu item with id {} not found", id);
			throw new ItemNotFoundException(String.valueOf(id), "Unable to delete.A menu item with id");
		}

		menuItemService.deleteMenuItemById(id);
		return new ResponseEntity<MenuItem>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete all MenuItem--------------------------------
	@ApiOperation(value = "Delete all MenuItem")
	@DeleteMapping
	public ResponseEntity<MenuItem> deleteMenuItem() {
		logger.info("Delete All Menu Items");
		menuItemService.deleteAllMenuItems();
		return new ResponseEntity<MenuItem>(HttpStatus.NO_CONTENT);
	}

}
