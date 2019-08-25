package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<OrderedItem> order;
	
	public Order() {
		super();
	}

	public List<OrderedItem> getOrder() {
		return order;
	}

	public void setOrder(List<OrderedItem> order) {
		this.order = order;
	}
	
	

}
