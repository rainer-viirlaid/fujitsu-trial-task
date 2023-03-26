package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class RegionalBaseFeeDto {

    private String cityName;
    private String methodName;
    private BigDecimal fee;
}
