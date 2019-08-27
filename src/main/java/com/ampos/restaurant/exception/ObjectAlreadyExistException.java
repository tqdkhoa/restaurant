package com.ampos.restaurant.exception;

public class ObjectAlreadyExistException extends RuntimeException {

	public ObjectAlreadyExistException(String name) {
		super(String.format("Object %s already exists", name));
	}
}
