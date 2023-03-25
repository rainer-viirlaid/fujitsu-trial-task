package com.task.fooddelivery.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String type, String entity) {
        super("Entity '" + entity + "' of type '" + type + "' could not be found.");
    }
}
