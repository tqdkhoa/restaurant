package com.ampos.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ampos.restaurant.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
