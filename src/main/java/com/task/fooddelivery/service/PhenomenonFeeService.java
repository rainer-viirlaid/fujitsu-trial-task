package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.PhenomenonFeeDto;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.PhenomenonFee;
import com.task.fooddelivery.exception.DeliveryForbiddenException;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.PhenomenonFeeRepository;
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

    private final PhenomenonFeeRepository feeRepository;
    private final DeliveryMethodService methodService;

    public BigDecimal calculatePhenomenonFee(String phenomenon, DeliveryMethod method) {
        List<BigDecimal> fees = new ArrayList<>(List.of(new BigDecimal(0)));
        if (phenomenon == null) return fees.get(0);
        for (PhenomenonFee rule : method.getPhenomenonFees()) {
            if (phenomenon.toLowerCase(Locale.ROOT).contains(rule.getPhenomenonName().toLowerCase(Locale.ROOT))) {
                if (rule.isDeliveryForbidden()) throw new DeliveryForbiddenException(phenomenon);
                fees.add(rule.getFee());
            }
        }
        return fees.stream().max(Comparator.naturalOrder()).get();
    }

    public void addNewFee(PhenomenonFeeDto feeDto) {
        DeliveryMethod method = methodService.getDeliveryMethodEntity(feeDto.getDeliveryMethodName());
        PhenomenonFee fee = PhenomenonFee.builder()
                .phenomenonName(feeDto.getPhenomenonName())
                .deliveryForbidden(feeDto.isDeliveryForbidden())
                .deliveryMethod(method)
                .fee(feeDto.getFee())
                .build();
        feeRepository.save(fee);
    }

    public void deleteFees(String phenomenonName, String methodName) {
        DeliveryMethod method = methodService.getDeliveryMethodEntity(methodName);
        List<PhenomenonFee> fees = feeRepository.findAllByPhenomenonNameAndDeliveryMethod(phenomenonName, method);
        if (fees.isEmpty()) throw new NotFoundException("No suitable phenomenon fee rules found.");
        feeRepository.deleteAll(fees);
    }
}
