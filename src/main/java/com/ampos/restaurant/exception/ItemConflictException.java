package com.ampos.restaurant.exception;

public class ItemConflictException extends RuntimeException {

	public ItemConflictException(String name, String message) {
		super(String.format("%s %s already exists", message, name));
	}
}
