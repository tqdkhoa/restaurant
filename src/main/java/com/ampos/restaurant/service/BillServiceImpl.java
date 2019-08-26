package com.ampos.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ampos.restaurant.model.Bill;
import com.ampos.restaurant.repositories.BillRepository;

@Service("buildService")
@Transactional
public class BillServiceImpl implements BillService{

	@Autowired
	BillRepository repository;
	
	public Bill findById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public void saveBill(Bill bill) {
		repository.save(bill);
	}
	
	public void updateBill(Bill bill) {
		saveBill(bill);
	}
}
