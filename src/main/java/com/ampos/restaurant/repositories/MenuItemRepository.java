package com.ampos.restaurant.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ampos.restaurant.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	MenuItem findByName(String name);
	
	Long countByName(String name); 

	@Query( "SELECT distinct menuItem FROM MenuItem AS menuItem WHERE (:name is NULL) "
    		+ " OR (LOWER(menuItem.name) LIKE CONCAT('%',:name,'%'))"
    		+ " OR (LOWER(menuItem.description) LIKE CONCAT('%',:name,'%'))"
    		+ " OR (LOWER(menuItem.details) LIKE CONCAT('%',:name,'%'))")
	List<MenuItem> findByNameIgnoreCaseOrDescriptionIgnoreCaseOrDetailsIgnoreCase(@Param("name")String name, Pageable pageable);
	
	@Query("SELECT distinct menuItem FROM MenuItem AS menuItem WHERE menuItem.id IN (:ids)")
	List<MenuItem> findMenuByInIds(@Param("ids") Collection<Long> ids);
	
	@Query("SELECT distinct menuItem FROM MenuItem AS menuItem WHERE menuItem.name IN (:names)")
	List<MenuItem> findMenuByInNames(@Param("names") Collection<String> names);
}
