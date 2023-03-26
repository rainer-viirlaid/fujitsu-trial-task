package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PhenomenonFeeDeletionDto {
    private String phenomenonName;
    private String deliveryMethod;
}
