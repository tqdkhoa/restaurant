package com.ampos.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ampos.restaurant.model.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

}
