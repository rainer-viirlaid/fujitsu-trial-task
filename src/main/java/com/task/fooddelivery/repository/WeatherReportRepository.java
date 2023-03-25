package com.task.fooddelivery.repository;

import com.task.fooddelivery.entity.WeatherReport;
import com.task.fooddelivery.entity.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherReportRepository extends JpaRepository<WeatherReport, Integer> {

    List<WeatherReport> findAllByWeatherStationAndTimestampBetween(WeatherStation station, LocalDateTime startTime, LocalDateTime endTime);
}
