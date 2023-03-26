package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.City;
import com.task.fooddelivery.entity.WeatherStation;
import com.task.fooddelivery.exception.AlreadyExistsException;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;
    private final WeatherStationService stationService;

    public void addCity(String name, String stationName) {
        Optional<City> cityOptional = cityRepository.findByCityNameIgnoreCase(name);
        cityOptional.ifPresent(city -> {throw new AlreadyExistsException("city", city.getCityName());});
        WeatherStation station = stationService.getStationEntity(stationName);
        City city = City.builder().cityName(name).weatherStation(station).build();
        cityRepository.save(city);
    }

    public City getCityEntity(String cityName) {
        Optional<City> cityOptional = cityRepository.findByCityNameIgnoreCase(cityName);
        cityOptional.orElseThrow(() -> {throw new NotFoundException("city", cityName);});
        return cityOptional.get();
    }

    public String getCityInformation(String cityName) {
        City city = getCityEntity(cityName);
        return city.toString();
    }

    public void updateCity(String oldName, String newName, String stationName) {
        City city = getCityEntity(oldName);
        WeatherStation station = stationService.getStationEntity(stationName);
        city.setCityName(newName);
        city.setWeatherStation(station);
        cityRepository.save(city);
    }

    public void deleteCity(String cityName) {
        City city = getCityEntity(cityName);
        cityRepository.delete(city);
    }
}
