package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AirTemperatureFeeDeletionDto {
    private Double minTemp;
    private Double maxTemp;
    private String deliveryMethod;
}
