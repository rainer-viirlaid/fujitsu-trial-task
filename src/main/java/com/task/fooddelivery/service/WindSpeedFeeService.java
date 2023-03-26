package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.WindSpeedFeeDto;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.WindSpeedFee;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import com.task.fooddelivery.repository.WindSpeedFeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class WindSpeedFeeService {

    private final WindSpeedFeeRepository feeRepository;
    private final DeliveryMethodService methodService;

    public BigDecimal calculateWindSpeedFee(Double windSpeed, DeliveryMethod method) {
        List<BigDecimal> fees = new ArrayList<>(List.of(new BigDecimal(0)));
        if (windSpeed == null) return fees.get(0);
        for (WindSpeedFee rule : method.getWindSpeedFees()) {
            if (rule.getMinSpeedNotNull() <= windSpeed && windSpeed <= rule.getMaxSpeedNotNull()) {
                if (rule.isDeliveryForbidden()) throw new DeliveryForbiddenException("wind speed (" + windSpeed + " m/s)");
                fees.add(rule.getFee());
            }
        }
        return fees.stream().max(Comparator.naturalOrder()).get();
    }

    public void addNewFee(WindSpeedFeeDto feeDto) {
        DeliveryMethod method = methodService.getDeliveryMethodEntity(feeDto.getDeliveryMethodName());
        WindSpeedFee fee = WindSpeedFee.builder()
                .minSpeed(feeDto.getMinSpeed())
                .maxSpeed(feeDto.getMaxSpeed())
                .deliveryForbidden(feeDto.isDeliveryForbidden())
                .deliveryMethod(method)
                .fee(feeDto.getFee())
                .build();
        feeRepository.save(fee);
    }

    public void deleteFees(Double minSpeed, Double maxSpeed, String methodName) {
        DeliveryMethod method = methodService.getDeliveryMethodEntity(methodName);
        List<WindSpeedFee> fees = feeRepository.findAllByMinSpeedAndMaxSpeedAndDeliveryMethod(minSpeed, maxSpeed, method);
        feeRepository.deleteAll(fees);
    }
}
