package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.City;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFee, Integer> {

    Optional<RegionalBaseFee> findByCityAndDeliveryMethod(City city, DeliveryMethod method);
}
