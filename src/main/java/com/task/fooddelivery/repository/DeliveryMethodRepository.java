package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Integer> {

    Optional<DeliveryMethod> findByMethodNameIgnoreCase(String methodName);
}
