package com.ampos.restaurant.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "bill_menu_item")
public class BillDetail {

	@EmbeddedId
	private BillDetailKey id;

	@ManyToOne
	@MapsId("menu_item_id")
	@JoinColumn(name = "menu_item_id")
	private MenuItem menuItem;

	@ManyToOne
	@MapsId("bill_id")
	@JoinColumn(name = "bill_id")
	private Bill bill;

	@Column(name = "quantities")
	private Integer quantities;

	@Column(name = "odered_time")
	private Date orderedTime;

	public BillDetail(BillDetailKey id, Integer quantities, Date ordered_time) {
		super();
		this.id = id;
		this.quantities = quantities;
		this.orderedTime = ordered_time;
	}
	
	public Double totalCost() {
		return menuItem.getPrice() * this.quantities;
	}

}
