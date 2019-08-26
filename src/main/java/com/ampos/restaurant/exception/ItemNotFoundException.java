package com.ampos.restaurant.exception;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(String id, String message) {
		super(String.format("%s %d not found ", message, id));
	}
}
