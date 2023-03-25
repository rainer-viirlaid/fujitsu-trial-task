package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class WeatherFeeService {

    private final CityService cityService;
    private final DeliveryMethodService methodService;
    private final WeatherReportService weatherReportService;
    private final WindSpeedFeeService windSpeedFeeService;
    private final AirTemperatureFeeService airTemperatureFeeService;
    private final PhenomenonFeeService phenomenonFeeService;

    public BigDecimal getWeatherFee(String cityName, String methodName, LocalDateTime time) {
        BigDecimal fee = new BigDecimal(0);
        City city = cityService.getCityEntity(cityName);
        WeatherReport report = weatherReportService.getNearestReportInTime(city.getWeatherStation(), time);
        DeliveryMethod method = methodService.getDeliveryMethodEntity(methodName);
        fee = fee.add(phenomenonFeeService.calculatePhenomenonFee(report.getPhenomenon(), method));
        fee = fee.add(airTemperatureFeeService.calculateAirTemperatureFee(report.getAirTemperature(), method));
        fee = fee.add(windSpeedFeeService.calculateWindSpeedFee(report.getWindSpeed(), method));
        return fee;
    }
}
