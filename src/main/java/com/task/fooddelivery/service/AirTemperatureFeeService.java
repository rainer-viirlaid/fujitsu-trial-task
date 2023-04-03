package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.AirTemperatureFeeDto;
import com.task.fooddelivery.entity.AirTemperatureFee;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.AirTemperatureFeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class AirTemperatureFeeService {

    private final AirTemperatureFeeRepository feeRepository;
    private final DeliveryMethodService methodService;

    public BigDecimal calculateAirTemperatureFee(Double airTemperature, DeliveryMethod method) {
        List<BigDecimal> fees = new ArrayList<>();
        if (airTemperature == null) return new BigDecimal(0);
        for (AirTemperatureFee rule : method.getAirTemperatureFees()) {
            if (rule.getMinTempNotNull() <= airTemperature && airTemperature <= rule.getMaxTempNotNull()) {
                if (rule.isDeliveryForbidden()) throw new DeliveryForbiddenException("air temperature (" + airTemperature + "°C)");
                fees.add(rule.getFee());
            }
        }
        return fees.stream().max(Comparator.naturalOrder()).orElse(new BigDecimal(0));
    }

    public void addNewFee(AirTemperatureFeeDto feeDto) {
        DeliveryMethod method = methodService.getDeliveryMethodEntity(feeDto.getDeliveryMethodName());
        AirTemperatureFee fee = AirTemperatureFee.builder()
                .minTemp(feeDto.getMinTemp())
                .maxTemp(feeDto.getMaxTemp())
                .deliveryForbidden(feeDto.isDeliveryForbidden())
                .deliveryMethod(method)
                .fee(feeDto.getFee())
                .build();
        feeRepository.save(fee);
    }

    public void deleteFees(Double minTemp, Double maxTemp, String methodName) {
        DeliveryMethod method = methodService.getDeliveryMethodEntity(methodName);
        List<AirTemperatureFee> fees = feeRepository.findAllByMinTempAndMaxTempAndDeliveryMethod(minTemp, maxTemp, method);
        if (fees.isEmpty()) throw new NotFoundException("No suitable temperature fee rules found.");
        feeRepository.deleteAll(fees);
    }
}
