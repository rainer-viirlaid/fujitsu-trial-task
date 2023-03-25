package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.exception.AlreadyExistsException;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.DeliveryMethodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DeliveryMethodService {

    private final DeliveryMethodRepository methodRepository;

    public void addDeliveryMethod(String name) {
        Optional<DeliveryMethod> methodOptional = methodRepository.findByMethodNameIgnoreCase(name);
        methodOptional.ifPresent(method -> {throw new AlreadyExistsException("delivery method", method.getMethodName());});
        DeliveryMethod method = DeliveryMethod.builder().methodName(name).build();
        methodRepository.save(method);
    }

    public DeliveryMethod getDeliveryMethodEntity(String methodName) {
        Optional<DeliveryMethod> methodOptional = methodRepository.findByMethodNameIgnoreCase(methodName);
        methodOptional.orElseThrow(() -> {throw new NotFoundException("delivery method", methodName);});
        return methodOptional.get();
    }

    public boolean doesDeliveryMethodExist(String methodName) {
        try {
            getDeliveryMethodEntity(methodName);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public void updateDeliveryMethodName(String oldName, String newName) {
        DeliveryMethod method = getDeliveryMethodEntity(oldName);
        method.setMethodName(newName);
    }

    public void deleteDeliveryMethod(String methodName) {
        DeliveryMethod method = getDeliveryMethodEntity(methodName);
        methodRepository.delete(method);
    }
}
