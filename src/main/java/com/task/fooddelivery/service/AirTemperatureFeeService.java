package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.AirTemperatureFee;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class AirTemperatureFeeService {

    public BigDecimal calculateAirTemperatureFee(Double airTemperature, DeliveryMethod method) {
        if (airTemperature == null) return new BigDecimal(0);
        for (AirTemperatureFee rule : method.getAirTemperatureFees()) {
            if (checkIfTemperatureRuleApplies(airTemperature, rule)) {
                if (rule.isDeliveryForbidden()) throw new DeliveryForbiddenException("air temperature (" + airTemperature + "°C)");
                return rule.getFee();
            }
        }
        return new BigDecimal(0);
    }

    private boolean checkIfTemperatureRuleApplies(double temperature, AirTemperatureFee rule) {
        boolean minInBounds;
        boolean maxInBounds;
        if (rule.isMinStrict()) minInBounds = rule.getMinTempNotNull() < temperature;
        else minInBounds = rule.getMinTempNotNull() <= temperature;
        if (rule.isMaxStrict()) maxInBounds = rule.getMaxTempNotNull() > temperature;
        else maxInBounds = rule.getMaxTempNotNull() >= temperature;
        return minInBounds && maxInBounds;
    }
}
