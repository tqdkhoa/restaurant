package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{

	MenuItem findByName(String name);
}
