package com.ampos.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ampos.restaurant.model.BillDetail;
import com.ampos.restaurant.repositories.BillDetailRepository;

@Service("buildDetailService")
@Transactional
public class BillDetailServiceImpl implements BillDetailService{

	@Autowired
	private BillDetailRepository repository;
	
	public BillDetail findById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<BillDetail> findAllBillDetails(){
		return repository.findAll();
	}
	
	public void saveBillDetail(BillDetail billDetail) {
		repository.save(billDetail);
	}
	
	public void updateBillDetail(BillDetail billDetail) {
		saveBillDetail(billDetail);
	}
	
	public void deleteBillDetailById(Long id) {
		repository.deleteById(id);
	}
	
	public void deleteAllBillDetails() {
		repository.deleteAll();
	}
}
