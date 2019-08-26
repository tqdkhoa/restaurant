package com.ampos.restaurant.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderedItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Integer quantity;

	public OrderedItem(String name, Integer quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
	}
}
