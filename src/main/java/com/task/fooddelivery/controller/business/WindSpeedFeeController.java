package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.dto.WindSpeedFeeDeletionDto;
import com.task.fooddelivery.dto.WindSpeedFeeDto;
import com.task.fooddelivery.service.WindSpeedFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class WindSpeedFeeController {

    private final WindSpeedFeeService feeService;

    /**
     * Save a new wind speed fee into the database.
     *
     * @param feeDto DTO containing fee parameters
     */
    @PostMapping("/business/wind_speed_fee/create")
    public void addWindSpeedFee(@RequestBody WindSpeedFeeDto feeDto) {
        feeService.addNewFee(feeDto);
    }

    /**
     * Delete matching wind speed fees from the database.
     *
     * @param deletionDto DTO containing minimum wind speed, maximum wind speed and delivery method name
     */
    @DeleteMapping("/business/wind_speed_fee/delete")
    public void deleteWindSpeedFee(@RequestBody WindSpeedFeeDeletionDto deletionDto) {
        feeService.deleteFees(deletionDto.getMinSpeed(), deletionDto.getMaxSpeed(), deletionDto.getDeliveryMethod());
    }
}
