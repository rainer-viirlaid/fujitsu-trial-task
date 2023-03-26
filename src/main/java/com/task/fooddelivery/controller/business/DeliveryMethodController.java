package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.service.DeliveryMethodService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class DeliveryMethodController {

    private final DeliveryMethodService methodService;

    /**
     * Save a new delivery method into the database.
     *
     * @param methodName name of the delivery method
     */
    @PostMapping("/business/delivery_method/create")
    public void addDeliveryMethod(@RequestParam("method") String methodName) {
        methodService.addDeliveryMethod(methodName);
    }

    /**
     * Check if the delivery method already exists in the database.
     *
     * @param methodName name of the delivery method
     * @return whether the delivery method exists in the database
     */
    @GetMapping("/business/delivery_method/read")
    public boolean getDeliveryMethod(@RequestParam("method") String methodName) {
        return methodService.doesDeliveryMethodExist(methodName);
    }

    /**
     * Change the name of a delivery method.
     *
     * @param oldName old delivery method name
     * @param newName new delivery method name
     */
    @PutMapping("/business/delivery_method/update")
    public void updateDeliveryMethodName(@RequestParam("old_name") String oldName, @RequestParam("new_name") String newName) {
        methodService.updateDeliveryMethodName(oldName, newName);
    }

    /**
     * Delete a delivery method from the database.
     * <p>
     * Any related regional base fees must be deleted beforehand.
     *
     * @param methodName name of the delivery method
     */
    @DeleteMapping("/business/delivery_method/delete")
    public void deleteDeliveryMethod(@RequestParam("method") String methodName) {
        methodService.deleteDeliveryMethod(methodName);
    }
}
