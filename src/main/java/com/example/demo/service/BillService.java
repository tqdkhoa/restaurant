package com.example.demo.service;

import com.example.demo.model.Bill;

public interface BillService {

	Bill findById(Long id);
	
	void saveBill(Bill bill);
	
	void updateBill(Bill bill);
	
}
