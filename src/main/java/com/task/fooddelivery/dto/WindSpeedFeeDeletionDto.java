package com.task.fooddelivery.dto;

import lombok.Data;

@Data
public class WindSpeedFeeDeletionDto {
    private Double minSpeed;
    private Double maxSpeed;
    private String deliveryMethod;
}
