package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.dto.RegionalBaseFeeDto;
import com.task.fooddelivery.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class RegionalBaseFeeController {

    private final RegionalBaseFeeService baseFeeService;

    /**
     * Save a new regional base fee into the database.
     *
     * @param rbfDto DTO containing the city, delivery method and fee
     */
    @PostMapping("/business/regional_base_fee/create")
    public void addRegionalBaseFee(@RequestBody RegionalBaseFeeDto rbfDto) {
        baseFeeService.addRegionalBaseFee(rbfDto.getCityName(), rbfDto.getMethodName(), rbfDto.getFee());
    }

    /**
     * Get the regional base fee according to city and delivery method.
     *
     * @param cityName name of the city
     * @param methodName name of the delivery method
     * @return base fee
     */
    @GetMapping("/business/regional_base_fee/read")
    public BigDecimal getRegionalBaseFee(@RequestParam("city") String cityName, @RequestParam("method") String methodName) {
        return baseFeeService.getRegionalBaseFee(cityName, methodName);
    }

    /**
     * Change the regional base fee for the given city and delivery method.
     *
     * @param rbfDto DTO containing the city, delivery method and a new base fee
     */
    @PutMapping("/business/regional_base_fee/update")
    public void updateRegionalBaseFee(@RequestBody RegionalBaseFeeDto rbfDto) {
        baseFeeService.updateRegionalBaseFee(rbfDto);
    }

    /**
     * Delete a regional base fee from the database.
     *
     * @param cityName name of the city
     * @param methodName name of the delivery method
     */
    @DeleteMapping("/business/regional_base_fee/delete")
    public void deleteRegionalBaseFee(@RequestParam("city") String cityName, @RequestParam("method") String methodName) {
        baseFeeService.deleteRegionalBaseFee(cityName, methodName);
    }
}
