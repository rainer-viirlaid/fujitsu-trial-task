package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.DeliveryFeeDto;
import com.task.fooddelivery.dto.DeliveryFeeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryFeeService {

    private final RegionalBaseFeeService baseFeeService;

    public DeliveryFeeDto calculateDeliveryFee(DeliveryFeeRequestDto deliveryFeeRequestDto) {
        // TODO: weather fee
        DeliveryFeeDto feeDto = new DeliveryFeeDto();
        feeDto.fee = baseFeeService.getRegionalBaseFee(deliveryFeeRequestDto.city, deliveryFeeRequestDto.method);
        return feeDto;
    }
}
