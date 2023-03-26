package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.DeliveryFeeDto;
import com.task.fooddelivery.dto.DeliveryFeeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class DeliveryFeeService {

    private final RegionalBaseFeeService baseFeeService;
    private final WeatherFeeService weatherFeeService;

    public DeliveryFeeDto calculateDeliveryFee(DeliveryFeeRequestDto deliveryFeeRequestDto) {
        LocalDateTime time = LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC);
        if (deliveryFeeRequestDto.getTime() != null) time = LocalDateTime.ofInstant(deliveryFeeRequestDto.getTime().toInstant(), ZoneOffset.UTC);
        DeliveryFeeDto feeDto = new DeliveryFeeDto();
        BigDecimal baseFee = baseFeeService.getRegionalBaseFee(deliveryFeeRequestDto.getCity(), deliveryFeeRequestDto.getMethod());
        BigDecimal weatherFee = weatherFeeService.getWeatherFee(
                deliveryFeeRequestDto.getCity(),
                deliveryFeeRequestDto.getMethod(),
                time);
        feeDto.setFee(baseFee.add(weatherFee));
        return feeDto;
    }
}
