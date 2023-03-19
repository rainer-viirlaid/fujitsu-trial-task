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

    @GetMapping("/fee")
    public DeliveryFeeDto getDeliveryFee(@RequestBody DeliveryFeeRequestDto deliveryFeeRequestDto) {
        return deliveryFeeService.calculateDeliveryFee(deliveryFeeRequestDto);
    }
}
