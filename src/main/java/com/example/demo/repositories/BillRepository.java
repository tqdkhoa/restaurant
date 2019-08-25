package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{

}
