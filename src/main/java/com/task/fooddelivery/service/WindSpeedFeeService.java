package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.WindSpeedFee;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class WindSpeedFeeService {

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
}
