package com.task.fooddelivery.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AirTemperatureFeeDto {

    private Double minTemp;
    private Double maxTemp;
    private boolean deliveryForbidden;
    private String deliveryMethodName;
    private BigDecimal fee;
}
