package com.task.fooddelivery.controller.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DeliveryMethodControllerTest {

    @Autowired
    private MockMvc mvc;

    private final List<String> defaultDeliveryMethods = List.of(
            "Car",
            "Bike",
            "Scooter"
    );

    @Test
    void getDefaultDeliveryMethodsTest() throws Exception {
        for (String method : defaultDeliveryMethods) {
            mvc.perform(get("/business/delivery_method/read").param("method", method))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void createDeliveryMethodTest() throws Exception {
        String method = "createDeliveryMethodTest";
        mvc.perform(get("/business/delivery_method/read").param("method", method))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
        mvc.perform(post("/business/delivery_method/create").param("method", method))
                .andExpect(status().isOk());
        mvc.perform(get("/business/delivery_method/read").param("method", method))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void updateDeliveryMethodTest() throws Exception {
        String method = "updateDeliveryMethodTest";
        String methodNew = "updateDeliveryMethodTestRenamed";
        mvc.perform(put("/business/delivery_method/update").param("old_name", method).param("new_name", methodNew))
                .andExpect(status().isOk());
        mvc.perform(get("/business/delivery_method/read").param("method", methodNew))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void deleteDeliveryMethodTest() throws Exception {
        String method = "deleteDeliveryMethodTest";
        mvc.perform(delete("/business/delivery_method/delete").param("method", method))
                .andExpect(status().isOk());
        mvc.perform(get("/business/delivery_method/read").param("method", method))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void cannotDeleteDeliveryMethodBeforeRegionalBaseFeeTest() throws Exception {
        String method = defaultDeliveryMethods.get(0);
        mvc.perform(delete("/business/delivery_method/delete").param("method", method))
                .andExpect(status().is5xxServerError());
        mvc.perform(get("/business/delivery_method/read").param("method", method))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

}