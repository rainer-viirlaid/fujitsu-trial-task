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
        if (deliveryFeeRequestDto.time != null) time = LocalDateTime.ofInstant(deliveryFeeRequestDto.time.toInstant(), ZoneOffset.UTC);
        DeliveryFeeDto feeDto = new DeliveryFeeDto();
        BigDecimal baseFee = baseFeeService.getRegionalBaseFee(deliveryFeeRequestDto.city, deliveryFeeRequestDto.method);
        BigDecimal weatherFee = weatherFeeService.getWeatherFee(
                deliveryFeeRequestDto.city,
                deliveryFeeRequestDto.method,
                time);
        feeDto.fee = baseFee.add(weatherFee);
        return feeDto;
    }
}
