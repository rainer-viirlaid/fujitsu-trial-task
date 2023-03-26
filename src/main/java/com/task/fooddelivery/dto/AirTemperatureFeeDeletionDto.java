package com.task.fooddelivery.dto;

import lombok.Data;

@Data
public class AirTemperatureFeeDeletionDto {
    private Double minTemp;
    private Double maxTemp;
    private String deliveryMethod;
}
