package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.PhenomenonFee;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class PhenomenonFeeService {

    public BigDecimal calculatePhenomenonFee(String phenomenon, DeliveryMethod method) {
        List<BigDecimal> fees = new ArrayList<>(List.of(new BigDecimal(0)));
        if (phenomenon == null) return fees.get(0);
        for (PhenomenonFee rule : method.getPhenomenonFees()) {
            if (rule.getPhenomenonName().toLowerCase(Locale.ROOT).contains(phenomenon)) {
                if (rule.isDeliveryForbidden()) throw new DeliveryForbiddenException(phenomenon);
                fees.add(rule.getFee());
            }
        }
        return fees.stream().max(Comparator.naturalOrder()).get();
    }
}
