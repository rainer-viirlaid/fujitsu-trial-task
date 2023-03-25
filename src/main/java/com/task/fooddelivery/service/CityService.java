package com.task.fooddelivery.service;

import com.task.fooddelivery.entity.City;
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

    public void addCity(String name) {
        Optional<City> cityOptional = cityRepository.findByCityNameIgnoreCase(name);
        cityOptional.ifPresent(city -> {throw new AlreadyExistsException("city", city.getCityName());});
        City city = City.builder().cityName(name).build();
        cityRepository.save(city);
    }

    public City getCityEntity(String cityName) {
        Optional<City> cityOptional = cityRepository.findByCityNameIgnoreCase(cityName);
        cityOptional.orElseThrow(() -> {throw new NotFoundException("city", cityName);});
        return cityOptional.get();
    }

    public boolean doesCityExist(String cityName) {
        try {
            getCityEntity(cityName);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public void updateCityName(String oldName, String newName) {
        City city = getCityEntity(oldName);
        city.setCityName(newName);
    }

    public void deleteCity(String cityName) {
        City city = getCityEntity(cityName);
        cityRepository.delete(city);
    }
}
