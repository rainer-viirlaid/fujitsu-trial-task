package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.WeatherStation;
import com.task.fooddelivery.exception.AlreadyExistsException;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.WeatherStationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class WeatherStationService {

    private final WeatherStationRepository stationRepository;

    public void addWeatherStation(String name) {
        Optional<WeatherStation> stationOptional = stationRepository.findByStationNameIgnoreCase(name);
        stationOptional.ifPresent(station -> {throw new AlreadyExistsException("weather station", station.getStationName());});
        WeatherStation station = WeatherStation.builder().stationName(name).build();
        stationRepository.save(station);
    }

    public WeatherStation getStationEntity(String stationName) {
        Optional<WeatherStation> stationOptional = stationRepository.findByStationNameIgnoreCase(stationName);
        return stationOptional.orElseThrow(() -> new NotFoundException("weather station", stationName));
    }

    public boolean doesWeatherStationExist(String stationName) {
        try {
            getStationEntity(stationName);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public void updateWeatherStationName(String oldName, String newName) {
        WeatherStation station = getStationEntity(oldName);
        station.setStationName(newName);
        stationRepository.save(station);
    }

    public void deleteWeatherStation(String stationName) {
        WeatherStation station = getStationEntity(stationName);
        stationRepository.delete(station);
    }
}
