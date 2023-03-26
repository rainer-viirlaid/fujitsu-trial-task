package com.task.fooddelivery.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DeliveryFeeRequestDto {

    private String city;
    private String method;
    private OffsetDateTime time;
}
