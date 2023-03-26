package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.WindSpeedFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WindSpeedFeeRepository extends JpaRepository<WindSpeedFee, Integer> {

    List<WindSpeedFee> findAllByMinSpeedAndMaxSpeedAndDeliveryMethod(Double minSpeed, Double maxSpeed, DeliveryMethod method);
}
