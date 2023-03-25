package com.task.fooddelivery.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String type, String entity) {
        super("Entity '" + entity + "' of type '" + type + "' already exists.");
    }
}
