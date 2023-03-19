package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface DeliveryMethodRepository extends JpaRepositoryImplementation<DeliveryMethod, Long> {
}
