package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
}
