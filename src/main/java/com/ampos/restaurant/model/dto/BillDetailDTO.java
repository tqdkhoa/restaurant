package com.ampos.restaurant.model.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BillDetailDTO implements Serializable{

	private Long billId;
	private String menuItem;
	private int quantity;
	private Date orderedTime;
	private double subTotal;
}
