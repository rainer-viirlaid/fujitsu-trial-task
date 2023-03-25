package com.task.fooddelivery.controller;

import com.task.fooddelivery.dto.RegionalBaseFeeDto;
import com.task.fooddelivery.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class BusinessRulesController {

    private final CityService cityService;
    private final DeliveryMethodService methodService;
    private final RegionalBaseFeeService baseFeeService;

    /**
     * Save a new city into the database.
     *
     * @param cityName name of the city
     */
    @PostMapping("/business/add/city")
    public void addCity(@RequestParam("city") String cityName) {
        cityService.addCity(cityName);
    }

    /**
     * Save a new delivery method into the database.
     *
     * @param methodName name of the delivery method
     */
    @PostMapping("/business/add/delivery_method")
    public void addDeliveryMethod(@RequestParam("method") String methodName) {
        methodService.addDeliveryMethod(methodName);
    }

    /**
     * Save a new regional base fee into the database.
     *
     * @param rbfDto DTO containing the city, delivery method and fee
     */
    @PostMapping("/business/add/regional_base_fee")
    public void addRegionalBaseFee(@RequestBody RegionalBaseFeeDto rbfDto) {
        baseFeeService.addRegionalBaseFee(rbfDto.getCityName(), rbfDto.getMethodName(), rbfDto.getFee());
    }

    /**
     * Check if the city already exists in the database.
     *
     * @param cityName name of the city
     * @return whether the city exists in the database
     */
    @GetMapping("/business/get/city")
    public boolean getCity(@RequestParam("city") String cityName) {
        return cityService.doesCityExist(cityName);
    }

    /**
     * Check if the delivery method already exists in the database.
     *
     * @param methodName name of the delivery method
     * @return whether the delivery method exists in the database
     */
    @GetMapping("/business/get/delivery_method")
    public boolean getDeliveryMethod(@RequestParam("method") String methodName) {
        return methodService.doesDeliveryMethodExist(methodName);
    }

    /**
     * Get the regional base fee according to city and delivery method.
     *
     * @param cityName name of the city
     * @param methodName name of the delivery method
     * @return base fee
     */
    @GetMapping("/business/get/regional_base_fee")
    public BigDecimal getRegionalBaseFee(@RequestParam("city") String cityName, @RequestParam("method") String methodName) {
        return baseFeeService.getRegionalBaseFee(cityName, methodName);
    }

    /**
     * Change the name of a city.
     *
     * @param oldName old city name
     * @param newName new city name
     */
    @PutMapping("/business/update/city")
    public void updateCityName(@RequestParam("old_name") String oldName, @RequestParam("new_name") String newName) {
        cityService.updateCityName(oldName, newName);
    }

    /**
     * Change the name of a delivery method.
     *
     * @param oldName old delivery method name
     * @param newName new delivery method name
     */
    @PutMapping("/business/update/delivery_method")
    public void updateDeliveryMethodName(@RequestParam("old_name") String oldName, @RequestParam("new_name") String newName) {
        methodService.updateDeliveryMethodName(oldName, newName);
    }

    /**
     * Change the regional base fee for the given city and delivery method.
     *
     * @param rbfDto DTO containing the city, delivery method and a new base fee
     */
    @PutMapping("/business/update/regional_base_fee")
    public void updateRegionalBaseFee(@RequestBody RegionalBaseFeeDto rbfDto) {
        baseFeeService.updateRegionalBaseFee(rbfDto);
    }

    /**
     * Delete a city from the database.
     * <p>
     * Any related regional base fees must be deleted beforehand.
     *
     * @param cityName name of the city
     */
    @DeleteMapping("/business/delete/city")
    public void deleteCity(@RequestParam("city") String cityName) {
        cityService.deleteCity(cityName);
    }

    /**
     * Delete a delivery method from the database.
     * <p>
     * Any related regional base fees must be deleted beforehand.
     *
     * @param methodName name of the delivery method
     */
    @DeleteMapping("/business/delete/delivery_method")
    public void deleteDeliveryMethod(@RequestParam("method") String methodName) {
        methodService.deleteDeliveryMethod(methodName);
    }

    /**
     * Delete a regional base fee from the database.
     *
     * @param cityName name of the associated city
     * @param methodName name of the associated delivery method
     */
    @DeleteMapping("/business/delete/regional_base_fee")
    public void deleteRegionalBaseFee(@RequestParam("city") String cityName, @RequestParam("method") String methodName) {
        baseFeeService.deleteRegionalBaseFee(cityName, methodName);
    }
}
