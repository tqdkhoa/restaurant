package com.ampos.restaurant.model;

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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="menu_item")
@ApiModel(description = "All details about the Menu. ")
public class MenuItem implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="menu_item_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated Menu Item ID")
	private Long Id;
	
	@NotEmpty
	@Column(name="name", nullable=false)
	@ApiModelProperty(notes = "Menu item title")
	private String name;
	
	@NotEmpty
	@Column(name="description", nullable=false)
	@ApiModelProperty(notes = "Menu item description")
	private String description;
	
	@NotEmpty
	@Column(name="image_url", nullable=false)
	@ApiModelProperty(notes = "URL for image of menu item")
	private String imageUrl;
	
	@NotNull
	@Column(name="price", nullable=false)
	@ApiModelProperty(notes = "Price of menu item")
	private Double price;
	
	@Column(name="details", nullable=true)
	@ApiModelProperty(notes = "Additional description for menu item")
	private String details;
	
	@OneToMany(mappedBy = "menuItem")
	Set<BillDetail> billitems; 

}
