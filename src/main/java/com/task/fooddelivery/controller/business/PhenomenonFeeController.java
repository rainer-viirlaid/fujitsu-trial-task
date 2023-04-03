package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.dto.PhenomenonFeeDeletionDto;
import com.task.fooddelivery.dto.PhenomenonFeeDto;
import com.task.fooddelivery.service.PhenomenonFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PhenomenonFeeController {

    private final PhenomenonFeeService feeService;

    /**
     * Save a new phenomenon fee into the database.
     *
     * @param feeDto DTO containing fee parameters
     */
    @PostMapping("/business/phenomenon_fee/create")
    public void addPhenomenonFee(@RequestBody PhenomenonFeeDto feeDto) {
        feeService.addNewFee(feeDto);
    }

    /**
     * Delete matching phenomenon fees from the database.
     *
     * @param deletionDto DTO containing the phenomenon name and delivery method name
     */
    @DeleteMapping("/business/phenomenon_fee/delete")
    public void deletePhenomenonFee(@RequestBody PhenomenonFeeDeletionDto deletionDto) {
        feeService.deleteFees(deletionDto.getPhenomenonName(), deletionDto.getDeliveryMethod());
    }
}
