package com.task.fooddelivery.controller.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.fooddelivery.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WindSpeedFeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void addWindSpeedFeeTest() throws Exception {
        String city = "addWindSpeedFeeTestCity";
        String time = "2000-01-01T12:00:00Z";
        String method = "car";
        WindSpeedFeeDto feeDto = WindSpeedFeeDto.builder()
                .maxSpeed(-90d).deliveryMethodName(method).fee(new BigDecimal("91")).build();

        mvc.perform(post("/business/wind_speed_fee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeDto)))
                .andExpect(status().isOk());

        DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                .city(city).method(method).time(OffsetDateTime.parse(time)).build();

        mvc.perform(get("/fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fee").value(91d));


        WindSpeedFeeDeletionDto deletionDto = WindSpeedFeeDeletionDto.builder()
                .maxSpeed(-90d).deliveryMethod(method).build();
        mvc.perform(delete("/business/wind_speed_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteWindSpeedTest() throws Exception {
        Double minSpeed = 88d;
        String method = "scooter";
        WindSpeedFeeDeletionDto deletionDto = WindSpeedFeeDeletionDto.builder()
                .minSpeed(minSpeed).deliveryMethod(method).build();

        mvc.perform(delete("/business/wind_speed_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().isOk());
        mvc.perform(delete("/business/wind_speed_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().is4xxClientError());
    }

}