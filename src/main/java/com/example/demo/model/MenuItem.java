package com.example.demo.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="menu_item")
public class MenuItem implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="menu_item_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long Id;
	
	@NotEmpty
	@Column(name="name", nullable=false)
	private String name;
	
	@NotEmpty
	@Column(name="description", nullable=false)
	private String description;
	
	@NotEmpty
	@Column(name="image_url", nullable=false)
	private String imageUrl;
	
	@NotNull
	@Column(name="price", nullable=false)
	private Double price;
	
	@Column(name="details", nullable=true)
	private String details;
	
	@OneToMany(mappedBy = "menuItem")
	Set<BillDetail> billitems; 

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<BillDetail> getBillitems() {
		return billitems;
	}

	public void setBillitems(Set<BillDetail> billitems) {
		this.billitems = billitems;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
