package com.ampos.restaurant.service;

import java.util.List;

import com.ampos.restaurant.model.BillDetail;

public interface BillDetailService {

    BillDetail findById(Long id);

    List<BillDetail> findAllBillDetails();

    void saveBillDetail(BillDetail billDetail);

    void updateBillDetail(BillDetail billDetail);

    void deleteBillDetailById(Long Id);

    void deleteAllBillDetails();

}
