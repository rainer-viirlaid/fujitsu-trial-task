package com.task.fooddelivery.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegionalBaseFeeDto {

    private String cityName;
    private String methodName;
    private BigDecimal fee;
}
