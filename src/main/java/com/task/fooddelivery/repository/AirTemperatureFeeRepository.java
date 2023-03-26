package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.AirTemperatureFee;
import com.task.fooddelivery.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirTemperatureFeeRepository extends JpaRepository<AirTemperatureFee, Integer> {

    List<AirTemperatureFee> findAllByMinTempAndMaxTempAndDeliveryMethod(Double minTemp, Double maxTemp, DeliveryMethod method);
}
