package com.ampos.restaurant.service;

import com.ampos.restaurant.model.Bill;

public interface BillService {

	Bill findById(Long id);
	
	void saveBill(Bill bill);
	
	void updateBill(Bill bill);
	
}
