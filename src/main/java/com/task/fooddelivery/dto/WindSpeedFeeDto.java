package com.task.fooddelivery.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WindSpeedFeeDto {

    private Double minSpeed;
    private Double maxSpeed;
    private boolean deliveryForbidden;
    private String deliveryMethodName;
    private BigDecimal fee;
}
