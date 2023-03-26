package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WindSpeedFeeDeletionDto {
    private Double minSpeed;
    private Double maxSpeed;
    private String deliveryMethod;
}
