package com.ampos.restaurant.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
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
import com.ampos.restaurant.model.dto.BillDTO;
import com.ampos.restaurant.model.dto.BillDetailDTO;
import com.ampos.restaurant.model.dto.BillItemDTO;
import com.ampos.restaurant.model.dto.BillReportDTO;
import com.ampos.restaurant.model.dto.MenuItemDTO;
import com.ampos.restaurant.model.dto.OrderDTO;
import com.ampos.restaurant.model.dto.OrderedItemDTO;
import com.ampos.restaurant.service.BillDetailService;
import com.ampos.restaurant.service.BillService;
import com.ampos.restaurant.service.MenuItemService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	
	@Autowired
    private ModelMapper modelMapper;

	// -------------------Retrieve Bill--------------------------------
	@ApiOperation(value = "Retrieve Bill")
	@GetMapping("/{id}")
	public ResponseEntity<?> retrieveBill( 	@ApiParam(value = "Bill id from which bill object will retrieve", required = true) 
											@PathVariable("id") long id) throws JsonProcessingException{
		logger.info("Update Bill with id {}", id);
		Bill currentBill = billService.findById(id);
		if (currentBill == null) {
			logger.error("Unable to update build. Bill with id {} does not exist", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		
		BillReportDTO report = new BillReportDTO();
		report.setBillId(currentBill.getId());
		
		Double totalCost = 0.0;
		Set<BillDetail> billDetails = currentBill.getBillDetails();
		List<BillItemDTO> billItems = new ArrayList<BillItemDTO>();
		List<MenuItem> menus = menuItemService.findMenuByInIds(currentBill.getMenuIds());
		
		for (BillDetail billDetail : billDetails) {
			totalCost += billDetail.subTotal();
			MenuItem orderedMenu = menus.stream()
					.filter(menu -> menu.getId().equals(billDetail.getId().getMenuItemId())).findFirst().get();
			BillItemDTO billItem = new BillItemDTO(orderedMenu.getName(), billDetail.getQuantities(),
					billDetail.getOrderedTime().toGMTString());
			billItems.add(billItem);
		}
		report.setTotal(totalCost);
		report.setBillItems(billItems);
		
		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	// -------------------Create A Bill with Menu Item(s)-------------
	@ApiOperation(value = "Create A Bill with Menu Item(s)")
	@PostMapping
	public ResponseEntity<?> createBill(
			@ApiParam(value = "List of menus and their quantity", required = true)
			@RequestBody OrderDTO order) {
		
		Bill bill = new Bill();
		billService.saveBill(bill);
		
		Set<BillDetail> details = new HashSet<>();
		for (OrderedItemDTO item : order.getOrder()) {
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

		return new ResponseEntity<BillDTO>(convertToDTO(bill), HttpStatus.CREATED);
	}

	// -------------------Update Existing Bill-------------------------
	@ApiOperation(value = "Update Existing Bill")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBill(
			@ApiParam(value = "Bill id from which bill object will retrieve", required = true)
			@PathVariable("id") long id,
			@ApiParam(value = "List of menus and their quantity", required = true)
			@RequestBody OrderDTO order) {
		
		logger.info("Update Bill with id {}", id);
		Bill currentBill = billService.findById(id);
		if (currentBill == null) {
			logger.error("Unable to update build. Bill with id {} does not exist", id);
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		Set<BillDetail> details = new HashSet<BillDetail>();
		List<MenuItem> menus = menuItemService.findMenuByInIds(currentBill.getMenuIds());
		
		for (OrderedItemDTO item : order.getOrder()) {
			logger.info("Create Build for {}, and quantity is {}", item.getName(), item.getQuantity());
			BillDetail billDetail = new BillDetail();
			MenuItem orderedMenu = menus.stream()
					.filter(menu -> menu.getName().equals(item.getName())).findFirst().get();
			billDetail.setId(new BillDetailKey(currentBill.getId(), orderedMenu.getId()));
			billDetail.setQuantities(item.getQuantity());
			billDetail.setOrderedTime(new Date());
			billDetailService.saveBillDetail(billDetail);
			details.add(billDetail);
		}
		currentBill.setBillDetails(details);

		return new ResponseEntity<BillDTO>(convertToDTO(currentBill), HttpStatus.OK);
	}
	
	private BillDTO convertToDTO(Bill bill) {
		
		Double totalCost = 0.0;
		BillDTO billDTO = modelMapper.map(bill, BillDTO.class);
		List<MenuItem> menus = menuItemService.findMenuByInIds(bill.getMenuIds());
		Set<BillDetailDTO> billDetailDTOs = new HashSet<>();
		for(BillDetail billDetail : bill.getBillDetails()) {
			BillDetailDTO billDetailDTO = modelMapper.map(billDetail, BillDetailDTO.class);
			MenuItem orderedMenu = menus.stream()
					.filter(menu -> menu.getId().equals(billDetail.getId().getMenuItemId())).findFirst().get();
			Double subTotal = orderedMenu.getPrice() * billDetail.getQuantities();
			totalCost += subTotal;
			billDetailDTO.setBillId(bill.getId());
			billDetailDTO.setMenuItem(orderedMenu.getName());
			billDetailDTO.setQuantity(billDetail.getQuantities());
			billDetailDTO.setSubTotal(subTotal);
			billDetailDTOs.add(billDetailDTO);
		}
		billDTO.setBillDetails(billDetailDTOs);
		billDTO.setTotal(totalCost);
		return billDTO;
	}
}
