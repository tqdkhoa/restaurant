package com.ampos.restaurant.model.dto;

import java.io.Serializable;

public class BillItemDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String menu;
	private Integer quatity;
	private String orderedTime;
	
	public BillItemDTO(String menu, Integer quatity, String orderedTime) {
		super();
		this.menu = menu;
		this.quatity = quatity;
		this.orderedTime = orderedTime;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public Integer getQuatity() {
		return quatity;
	}
	public void setQuatity(Integer quatity) {
		this.quatity = quatity;
	}
	public String getOrderedTime() {
		return orderedTime;
	}
	public void setOrderedTime(String orderedTime) {
		this.orderedTime = orderedTime;
	}
}
