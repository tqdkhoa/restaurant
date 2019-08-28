package com.ampos.restaurant.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ampos.restaurant.exception.ObjectNotFoundException;
import com.ampos.restaurant.model.Bill;
import com.ampos.restaurant.model.BillDetail;
import com.ampos.restaurant.model.BillDetailKey;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.Order;
import com.ampos.restaurant.model.OrderedItem;
import com.ampos.restaurant.model.report.BillItem;
import com.ampos.restaurant.model.report.BillReport;
import com.ampos.restaurant.service.BillDetailService;
import com.ampos.restaurant.service.BillService;
import com.ampos.restaurant.service.MenuItemService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/bills")
@Api(value="Bill Management", description="Bill Management")
public class BillController {

	private static final Logger logger = LoggerFactory.getLogger(BillController.class);

	@Autowired
	private BillService billService;

	@Autowired
	private BillDetailService billDetailService;

	@Autowired
	private MenuItemService menuItemService;

	// -------------------Retrieve Bill--------------------------------
	@ApiOperation(value = "Retrieve Bill")
	@GetMapping("/{id}")
	public ResponseEntity<?> retrieveBill(@PathVariable("id") long id) throws JsonProcessingException{
		logger.info("Update Bill with id {}", id);
		Bill currentBill = billService.findById(id);
		if (currentBill == null) {
			logger.error("Unable to update build. Bill with id {} does not exist", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		
		BillReport report = new BillReport();
		report.setBillId(currentBill.getId());
		
		Double totalCost = 0.0;
		Set<BillDetail> billDetails = currentBill.getBillDetails();
		List<BillItem> billItems = new ArrayList<BillItem>();
		for(BillDetail billDetail : billDetails) {
			totalCost +=  billDetail.totalCost();
			MenuItem menu = menuItemService.findById(billDetail.getId().getMenuItemId());
			BillItem billItem = new BillItem(menu.getName(), billDetail.getQuantities(), billDetail.getOrderedTime().toGMTString());
			billItems.add(billItem);
		}
		report.setTotal(totalCost);
		report.setBillItems(billItems);
		
		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	// -------------------Create A Bill with Menu Item(s)-------------
	@ApiOperation(value = "Create A Bill with Menu Item(s)")
	@PostMapping
	public ResponseEntity<?> createBill(@RequestBody Order order) {
		
		Bill bill = new Bill();
		billService.saveBill(bill);

		Set<BillDetail> details = new HashSet<>();
		for (OrderedItem item : order.getOrder()) {
			logger.info("Create bill for {}, and quantity is {}", item.getName(), item.getQuantity());
			BillDetail billDetail = new BillDetail();
			MenuItem menuItem = menuItemService.findByName(item.getName());
			if (menuItem == null) {
				logger.error("Unable to create bill. Item name {} does not exist", item.getName());
				throw new ObjectNotFoundException(item.getName());
			}
			billDetail.setId(new BillDetailKey(bill.getId(), menuItem.getId()));
			billDetail.setQuantities(item.getQuantity());
			billDetail.setOrderedTime(new Date());
			billDetailService.saveBillDetail(billDetail);
			details.add(billDetail);
		}
		bill.setBillDetails(details);

		return new ResponseEntity<Bill>(bill, HttpStatus.CREATED);
	}

	// -------------------Update Existing Bill-------------------------
	@ApiOperation(value = "Update Existing Bill")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBill(@PathVariable("id") long id, @RequestBody Order order) {
		
		logger.info("Update Bill with id {}", id);
		Bill currentBill = billService.findById(id);
		if (currentBill == null) {
			logger.error("Unable to update build. Bill with id {} does not exist", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		Set<BillDetail> details = new HashSet<BillDetail>();
		for (OrderedItem item : order.getOrder()) {
			logger.info("Create Build for {}, and quantity is {}", item.getName(), item.getQuantity());
			BillDetail billDetail = new BillDetail();
			MenuItem menuItem = menuItemService.findByName(item.getName());
			if (menuItem == null) {
				logger.error("Unable to create build. Item name {} does not exist", item.getName());
				throw new ObjectNotFoundException(item.getName());
			}
			billDetail.setId(new BillDetailKey(currentBill.getId(), menuItem.getId()));
			billDetail.setQuantities(item.getQuantity());
			billDetail.setOrderedTime(new Date());
			billDetailService.saveBillDetail(billDetail);
			details.add(billDetail);
		}
		currentBill.setBillDetails(details);

		return new ResponseEntity<Bill>(currentBill, HttpStatus.OK);
	}
}
