package com.ampos.restaurant.exception;

public class ApplicationRuntimeException extends RuntimeException {
	
	public ApplicationRuntimeException(String msg) {
		super(String.format("Error: %s", msg));
	}
}
