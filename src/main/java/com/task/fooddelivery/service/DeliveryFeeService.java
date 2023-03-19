package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.DeliveryFeeDto;
import com.task.fooddelivery.dto.DeliveryFeeRequestDto;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.repository.DeliveryMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryFeeService {

    private final DeliveryMethodRepository deliveryMethodRepository;

    public DeliveryFeeDto calculateDeliveryFee(DeliveryFeeRequestDto deliveryFeeRequestDto) {
        DeliveryMethod method = new DeliveryMethod();
        method.setMethodName("test");
        deliveryMethodRepository.save(method);
        return null;
    }
}
