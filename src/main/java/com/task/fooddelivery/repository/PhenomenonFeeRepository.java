package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.PhenomenonFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhenomenonFeeRepository extends JpaRepository<PhenomenonFee, Integer> {

    List<PhenomenonFee> findAllByPhenomenonNameAndDeliveryMethod(String phenomenonName, DeliveryMethod method);
}
