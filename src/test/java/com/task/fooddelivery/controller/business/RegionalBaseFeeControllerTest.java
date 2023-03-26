package com.task.fooddelivery.controller.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.fooddelivery.dto.RegionalBaseFeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RegionalBaseFeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private final Map<String, Map<String, BigDecimal>> defaultFeesMap = Map.of(
            "Tallinn", Map.of(
                    "car", new BigDecimal("4"),
                    "scooter", new BigDecimal("3.5"),
                    "bike", new BigDecimal("3")
            ),
            "Tartu", Map.of(
                    "car", new BigDecimal("3.5"),
                    "scooter", new BigDecimal("3"),
                    "bike", new BigDecimal("2.5")
            ),
            "Pärnu", Map.of(
                    "car", new BigDecimal("3"),
                    "scooter", new BigDecimal("2.5"),
                    "bike", new BigDecimal("2")
            )
    );

    @Test
    public void getDefaultBaseFeesTest() throws Exception {
        for (String city : defaultFeesMap.keySet()) {
            for (String deliveryMethod : defaultFeesMap.get(city).keySet()) {
                mvc.perform(get("/business/regional_base_fee/read").param("city", city).param("method", deliveryMethod))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$")
                                .value(defaultFeesMap.get(city).get(deliveryMethod).doubleValue()));
            }
        }
    }

    @Test
    public void createBaseFeeTest() throws Exception {
        String city = "createBaseFeeTestCity";
        String method = "car";
        BigDecimal fee = new BigDecimal("50.5");
        RegionalBaseFeeDto feeDto = RegionalBaseFeeDto.builder()
                .cityName(city).methodName(method).fee(fee).build();

        mvc.perform(post("/business/regional_base_fee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feeDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/business/regional_base_fee/read").param("city", city).param("method", method))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value(fee.doubleValue()));
    }

    @Test
    public void updateBaseFeeTest() throws Exception {
        String city = "updateBaseFeeTestCity";
        String method = "car";
        BigDecimal fee = new BigDecimal("55.5");
        RegionalBaseFeeDto feeDto = RegionalBaseFeeDto.builder()
                .cityName(city).methodName(method).fee(fee).build();

        mvc.perform(put("/business/regional_base_fee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feeDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/business/regional_base_fee/read").param("city", city).param("method", method))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value(fee.doubleValue()));
    }

    @Test
    public void deleteBaseFeeTest() throws Exception {
        String city = "deleteBaseFeeTestCity";
        String method = "car";

        mvc.perform(delete("/business/regional_base_fee/delete").param("city", city).param("method", method))
                .andExpect(status().isOk());
        mvc.perform(get("/business/regional_base_fee/read").param("city", city).param("method", method))
                .andExpect(status().is4xxClientError());
    }

}