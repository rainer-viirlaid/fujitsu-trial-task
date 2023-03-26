package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PhenomenonFeeDto {

    private String phenomenonName;
    private boolean deliveryForbidden;
    private String deliveryMethodName;
    private BigDecimal fee;
}
