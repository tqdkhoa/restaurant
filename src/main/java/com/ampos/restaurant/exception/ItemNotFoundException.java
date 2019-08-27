package com.ampos.restaurant.exception;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(String name) {
		super(String.format("Item %s not found", name));
	}
}
