package com.ampos.restaurant.model.report;

import java.io.Serializable;
import java.util.List;

public class BillReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long billId;
	private Double total;
	private List<BillItem> billItems;

	public BillReport() {
		
	}
	
	public BillReport(Long billId, Double total, List<BillItem> billItems) {
		super();
		this.billId = billId;
		this.total = total;
		this.billItems = billItems;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
	}
}
