package com.task.fooddelivery.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhenomenonFeeDto {

    private String phenomenonName;
    private boolean deliveryForbidden;
    private String deliveryMethodName;
    private BigDecimal fee;
}
