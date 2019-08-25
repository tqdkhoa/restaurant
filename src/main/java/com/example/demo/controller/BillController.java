package com.example.demo.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Bill;
import com.example.demo.model.BillDetail;
import com.example.demo.model.BillDetailKey;
import com.example.demo.model.MenuItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderedItem;
import com.example.demo.service.BillDetailService;
import com.example.demo.service.BillService;
import com.example.demo.service.MenuItemService;
import com.example.demo.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class BillController {

	public static final Logger logger = LoggerFactory.getLogger(BillController.class);

	@Autowired
	BillService billService;

	@Autowired
	BillDetailService billDetailService;

	@Autowired
	MenuItemService menuItemService;

	// -------------------Retrieve Bill--------------------------------
	@RequestMapping(value = "/bill/{id}", method = RequestMethod.GET)
	public ResponseEntity<Bill> retrieveBill(@PathVariable("id") long id){
		logger.info("Update Bill with id {}", id);
		Bill currentBill = billService.findById(id);
		if (currentBill == null) {
			logger.error("Unable to update build. Bill with id {} does not exist", id);
			return new ResponseEntity("Unable to update build. Bill with id " + id + " does not exist",
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Bill>(currentBill, HttpStatus.OK);
	}

	// -------------------Create A Bill with One Menu Item-------------
	@RequestMapping(value = "/bill/", method = RequestMethod.POST)
	public ResponseEntity<?> createBill(@RequestBody Order order) {
		Bill bill = new Bill();
		billService.saveBill(bill);

		Set<BillDetail> details = new HashSet<BillDetail>();
		for (OrderedItem item : order.getOrder()) {
			logger.info("Create Build for {}, and quantity is {}", item.getName(), item.getQuantity());
			BillDetail billDetail = new BillDetail();
			MenuItem menuItem = menuItemService.findByName(item.getName());
			if (menuItem == null) {
				logger.error("Unable to create build. Item name {} does not exist", item.getName());
				return new ResponseEntity(
						new CustomErrorType("Unable to create build. Item name " + item.getName() + " does not exist"),
						HttpStatus.NOT_FOUND);
			}
			billDetail.setId(new BillDetailKey(bill.getId(), menuItem.getId()));
			billDetail.setQuantities(item.getQuantity());
			billDetail.setOrdered_time(new Date());
			billDetailService.saveBillDetail(billDetail);
			details.add(billDetail);
		}
		bill.setBillDetails(details);

		return new ResponseEntity<Bill>(bill, HttpStatus.OK);
	}

	// -------------------Update Existing Bill-------------------------
	@RequestMapping(value = "/bill/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBill(@PathVariable("id") long id, @RequestBody Order order) {
		logger.info("Update Bill with id {}", id);
		Bill currentBill = billService.findById(id);
		if (currentBill == null) {
			logger.error("Unable to update build. Bill with id {} does not exist", id);
			return new ResponseEntity("Unable to update build. Bill with id " + id + " does not exist",
					HttpStatus.NOT_FOUND);
		}
		Set<BillDetail> details = new HashSet<BillDetail>();
		for (OrderedItem item : order.getOrder()) {
			logger.info("Create Build for {}, and quantity is {}", item.getName(), item.getQuantity());
			BillDetail billDetail = new BillDetail();
			MenuItem menuItem = menuItemService.findByName(item.getName());
			if (menuItem == null) {
				logger.error("Unable to create build. Item name {} does not exist", item.getName());
				return new ResponseEntity(
						new CustomErrorType("Unable to create build. Item name " + item.getName() + " does not exist"),
						HttpStatus.NOT_FOUND);
			}
			billDetail.setId(new BillDetailKey(currentBill.getId(), menuItem.getId()));
			billDetail.setQuantities(item.getQuantity());
			billDetail.setOrdered_time(new Date());
			billDetailService.saveBillDetail(billDetail);
			details.add(billDetail);
		}
		currentBill.setBillDetails(details);

		return new ResponseEntity<Bill>(currentBill, HttpStatus.OK);
	}
}
