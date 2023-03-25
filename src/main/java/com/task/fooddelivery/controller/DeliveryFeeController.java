package com.task.fooddelivery.controller;

import com.task.fooddelivery.dto.DeliveryFeeDto;
import com.task.fooddelivery.dto.DeliveryFeeRequestDto;
import com.task.fooddelivery.service.DeliveryFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    /**
     * Calculate the delivery fee according to the city, delivery method and weather.
     * <p>
     * By default, current weather data is used. If a timestamp is provided in the DTO, then weather data from that point in time
     * will be used.
     *
     * @param deliveryFeeRequestDto DTO that contains request parameters
     * @return total delivery fee
     */
    @GetMapping("/fee")
    public DeliveryFeeDto getDeliveryFee(@RequestBody DeliveryFeeRequestDto deliveryFeeRequestDto) {
        return deliveryFeeService.calculateDeliveryFee(deliveryFeeRequestDto);
    }
}
