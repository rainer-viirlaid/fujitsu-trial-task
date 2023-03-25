package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {

    Optional<City> findByCityNameIgnoreCase(String cityName);
}
