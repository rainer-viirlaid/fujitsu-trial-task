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
class PhenomenonFeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void addPhenomenonFeeTest() throws Exception {
        String city = "addPhenomenonFeeTestCity";
        String time = "2000-01-01T12:00:00Z";
        String method = "car";
        PhenomenonFeeDto feeDto = PhenomenonFeeDto.builder()
                .phenomenonName("alien invasion").deliveryMethodName(method).fee(new BigDecimal("999")).build();

        mvc.perform(post("/business/phenomenon_fee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeDto)))
                .andExpect(status().isOk());

        DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                .city(city).method(method).time(OffsetDateTime.parse(time)).build();

        mvc.perform(get("/fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fee").value(999d));


        PhenomenonFeeDeletionDto deletionDto = PhenomenonFeeDeletionDto.builder()
                .phenomenonName("alien invasion").deliveryMethod(method).build();
        mvc.perform(delete("/business/phenomenon_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePhenomenonFeeTest() throws Exception {
        String method = "scooter";
        PhenomenonFeeDeletionDto deletionDto = PhenomenonFeeDeletionDto.builder()
                .phenomenonName("volcanic eruption").deliveryMethod(method).build();

        mvc.perform(delete("/business/phenomenon_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().isOk());
        mvc.perform(delete("/business/phenomenon_fee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletionDto)))
                .andExpect(status().is4xxClientError());
    }

}