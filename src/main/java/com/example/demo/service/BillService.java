package com.example.demo.service;

import com.example.demo.model.Bill;

public interface BillService {

	Bill findById(Long id);
	
	public void saveBill(Bill bill);
	
	public void updateBill(Bill bill);
	
}
