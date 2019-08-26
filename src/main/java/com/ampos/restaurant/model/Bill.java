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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "bill")
@ApiModel(description = "All details about the Bill. ")
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "bill_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated Bill ID")
	private Long id;

	@OneToMany(mappedBy = "bill")
	@ApiModelProperty(notes = "List detail of a bill")
	Set<BillDetail> billDetails;

}
