package com.task.fooddelivery.service;

import com.task.fooddelivery.dto.RegionalBaseFeeDto;
import com.task.fooddelivery.entity.City;
import com.task.fooddelivery.entity.DeliveryMethod;
import com.task.fooddelivery.entity.RegionalBaseFee;
import com.task.fooddelivery.exception.AlreadyExistsException;
import com.task.fooddelivery.exception.NotFoundException;
import com.task.fooddelivery.repository.RegionalBaseFeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RegionalBaseFeeService {

    private final RegionalBaseFeeRepository baseFeeRepository;
    private final CityService cityService;
    private final DeliveryMethodService methodService;

    public void addRegionalBaseFee(String cityName, String methodName, BigDecimal fee) {
        City city = cityService.getCityEntity(cityName);
        DeliveryMethod method = methodService.getDeliveryMethodEntity(methodName);
        Optional<RegionalBaseFee> baseFeeOptional = baseFeeRepository.findByCityAndDeliveryMethod(city, method);
        baseFeeOptional.ifPresent(f -> {throw new AlreadyExistsException("base fee", cityName + ", " + methodName);});
        RegionalBaseFee baseFee = RegionalBaseFee.builder().city(city).deliveryMethod(method).fee(fee).build();
        baseFeeRepository.save(baseFee);
    }

    public BigDecimal getRegionalBaseFee(String cityName, String methodName) {
        return getRegionalBaseFeeEntity(cityName, methodName).getFee();
    }

    private RegionalBaseFee getRegionalBaseFeeEntity(String cityName, String methodName) {
        City city = cityService.getCityEntity(cityName);
        DeliveryMethod method = methodService.getDeliveryMethodEntity(methodName);
        Optional<RegionalBaseFee> baseFeeOptional = baseFeeRepository.findByCityAndDeliveryMethod(city, method);
        baseFeeOptional.orElseThrow(() -> {throw new NotFoundException("base fee", cityName + ", " + methodName);});
        return baseFeeOptional.get();
    }

    public void updateRegionalBaseFee(RegionalBaseFeeDto rbfDto) {
        RegionalBaseFee regionalBaseFee = getRegionalBaseFeeEntity(rbfDto.getCityName(), rbfDto.getMethodName());
        regionalBaseFee.setFee(rbfDto.getFee());
        baseFeeRepository.save(regionalBaseFee);
    }

    public void deleteRegionalBaseFee(String cityName, String methodName) {
        RegionalBaseFee baseFee = getRegionalBaseFeeEntity(cityName, methodName);
        baseFeeRepository.delete(baseFee);
    }
}
