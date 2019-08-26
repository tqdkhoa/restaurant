package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	MenuItem findByName(String name);

	@Query("select m from MenuItem m " 
			+ "where upper(m.name) like concat('%', upper(?1), '%') "
			+ "or upper(m.description) like concat('%', upper(?2), '%') "
			+ "or upper(m.details) like concat('%', upper(?3), '%')")
	List<MenuItem> findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(String name,
			String description, String details, Pageable pageable);
}
