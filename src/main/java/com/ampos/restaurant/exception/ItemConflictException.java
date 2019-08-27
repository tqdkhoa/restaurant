package com.ampos.restaurant.exception;

public class ItemConflictException extends RuntimeException {

	public ItemConflictException(String name) {
		super(String.format("Item %s already exists", name));
	}
}
