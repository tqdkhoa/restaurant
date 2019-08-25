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

@Entity
@Table(name = "bill")
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "bill_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@OneToMany(mappedBy = "bill")
	Set<BillDetail> billdetails;

	public Bill() {

	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Set<BillDetail> getBillDetails() {
		return billdetails;
	}

	public void setBillDetails(Set<BillDetail> billDetails) {
		this.billdetails = billDetails;
	}
}
