package com.task.fooddelivery.controller.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.fooddelivery.dto.AirTemperatureFeeDeletionDto;
import com.task.fooddelivery.dto.AirTemperatureFeeDto;
import com.task.fooddelivery.dto.DeliveryFeeRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AirTemperatureFeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    public void addAirTemperatureFeeTest() throws Exception {
        String city = "addAirTemperatureFeeTestCity";
        String time = "2000-01-01T12:00:00Z";
        String method = "car";
        AirTemperatureFeeDto feeDto = AirTemperatureFeeDto.builder()
                .minTemp(500d).deliveryMethodName(method).fee(new BigDecimal("99")).build();

        mvc.perform(post("/business/air_temperature_fee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeDto)))
                .andExpect(status().isOk());

        DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                .city(city).method(method).time(OffsetDateTime.parse(time)).build();

        mvc.perform(get("/fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fee").value(99d));


        AirTemperatureFeeDeletionDto deletionDto = AirTemperatureFeeDeletionDto.builder()
                .minTemp(500d).deliveryMethod(method).build();
        mvc.perform(delete("/business/air_temperature_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAirTemperatureTest() throws Exception {
        Double maxTemp = -88d;
        String method = "scooter";
        AirTemperatureFeeDeletionDto deletionDto = AirTemperatureFeeDeletionDto.builder()
                .maxTemp(maxTemp).deliveryMethod(method).build();

        mvc.perform(delete("/business/air_temperature_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().isOk());
        mvc.perform(delete("/business/air_temperature_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().is4xxClientError());
    }

}