package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.WeatherReport;
import com.task.fooddelivery.entity.WeatherStation;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.WeatherReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class WeatherReportService {

    private final WeatherReportRepository reportRepository;

    public WeatherReport getNearestReportInTime(WeatherStation station, LocalDateTime time) {
        List<WeatherReport> reports = reportRepository.findAllByWeatherStationAndTimestampBetween(station,
                time.minus(1, ChronoUnit.HOURS),
                time.plus(1, ChronoUnit.HOURS));
        if (reports.isEmpty()) throw new NotFoundException("No report from '" + station.getStationName() + "' found near given time. Please change requested timestamp or wait for new weather data to be retrieved.");
        return reports
                .stream()
                .sorted(Comparator.comparingLong(r -> Math.abs(
                        r.getTimestamp().toEpochSecond(ZoneOffset.UTC) - time.toEpochSecond(ZoneOffset.UTC)))
                )
                .toList().get(0);
    }
}
