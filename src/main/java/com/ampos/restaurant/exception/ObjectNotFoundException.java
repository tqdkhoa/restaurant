package com.ampos.restaurant.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String name) {
        super(String.format("Object %s not found", name));
    }
}
