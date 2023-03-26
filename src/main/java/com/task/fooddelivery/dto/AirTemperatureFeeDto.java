package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AirTemperatureFeeDto {

    private Double minTemp;
    private Double maxTemp;
    private boolean deliveryForbidden;
    private String deliveryMethodName;
    private BigDecimal fee;
}
