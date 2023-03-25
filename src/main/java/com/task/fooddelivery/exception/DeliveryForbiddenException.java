package com.task.fooddelivery.exception;

public class DeliveryForbiddenException extends RuntimeException{
    public final String reason;

    public DeliveryForbiddenException(String reason) {
        super("Usage of selected vehicle type is forbidden");
        this.reason = reason;
    }
}
