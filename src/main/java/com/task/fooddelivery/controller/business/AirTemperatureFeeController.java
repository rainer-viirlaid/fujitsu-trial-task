package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.dto.AirTemperatureFeeDeletionDto;
import com.task.fooddelivery.dto.AirTemperatureFeeDto;
import com.task.fooddelivery.service.AirTemperatureFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AirTemperatureFeeController {

    private final AirTemperatureFeeService feeService;

    /**
     * Save a new air temperature fee into the database.
     *
     * @param feeDto DTO containing fee parameters
     */
    @PostMapping("/business/air_temperature_fee/create")
    public void addAirTemperatureFee(@RequestBody AirTemperatureFeeDto feeDto) {
        feeService.addNewFee(feeDto);
    }

    /**
     * Delete matching air temperature fees from the database.
     *
     * @param deletionDto DTO containing minimum air temperature, maximum air temperature and delivery method name
     */
    @DeleteMapping("/business/air_temperature_fee/delete")
    public void deleteAirTemperatureFee(@RequestBody AirTemperatureFeeDeletionDto deletionDto) {
        feeService.deleteFees(deletionDto.getMinTemp(), deletionDto.getMaxTemp(), deletionDto.getDeliveryMethod());
    }
}
