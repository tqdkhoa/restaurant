package com.ampos.restaurant.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<OrderedItem> order;
}
