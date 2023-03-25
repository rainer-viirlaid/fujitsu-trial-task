package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherStationRepository extends JpaRepository<WeatherStation, Integer> {

    Optional<WeatherStation> findByStationName(String stationName);
}
