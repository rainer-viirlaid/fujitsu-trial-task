package com.task.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
public class DeliveryFeeRequestDto {

    private String city;
    private String method;
    private OffsetDateTime time;
}
