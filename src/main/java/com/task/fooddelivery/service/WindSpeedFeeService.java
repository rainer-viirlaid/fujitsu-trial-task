package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.WindSpeedFee;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class WindSpeedFeeService {

    public BigDecimal calculateWindSpeedFee(Double windSpeed, DeliveryMethod method) {
        if (windSpeed == null) return new BigDecimal(0);
        for (WindSpeedFee rule : method.getWindSpeedFees()) {
            if (checkIfWindSpeedRuleApplies(windSpeed, rule)) {
                if (rule.isDeliveryForbidden()) throw new DeliveryForbiddenException("wind speed (" + windSpeed + " m/s)");
                return rule.getFee();
            }
        }
        return new BigDecimal(0);
    }

    private boolean checkIfWindSpeedRuleApplies(double windSpeed, WindSpeedFee rule) {
        boolean minInBounds;
        boolean maxInBounds;
        if (rule.isMinStrict()) minInBounds = rule.getMinSpeedNotNull() < windSpeed;
        else minInBounds = rule.getMinSpeedNotNull() <= windSpeed;
        if (rule.isMaxStrict()) maxInBounds = rule.getMaxSpeedNotNull() > windSpeed;
        else maxInBounds = rule.getMaxSpeedNotNull() >= windSpeed;
        return minInBounds && maxInBounds;
    }
}
