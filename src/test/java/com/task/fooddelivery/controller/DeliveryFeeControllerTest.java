package com.task.fooddelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.fooddelivery.dto.DeliveryFeeRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DeliveryFeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private final Map<String, BigDecimal> defaultCityCarFees = Map.of(
            "Tallinn", new BigDecimal("4"),
            "Tartu", new BigDecimal("3.5"),
            "Pärnu", new BigDecimal("3")
    );

    private final Map<String, BigDecimal> defaultCityScooterBaseFees = Map.of(
            "Tallinn", new BigDecimal("3.5"),
            "Tartu", new BigDecimal("3"),
            "Pärnu", new BigDecimal("2.5")
    );

    private final Map<String, BigDecimal> defaultScooterWeatherFees = Map.of(
            "2000-01-01T01:00:00Z", new BigDecimal("0"),
            "2000-01-01T02:00:00Z", new BigDecimal("1"),
            "2000-01-01T03:00:00Z", new BigDecimal("0.5"),
            "2000-01-01T06:00:00Z", new BigDecimal("1"),
            "2000-01-01T07:00:00Z", new BigDecimal("1"),
            "2000-01-01T08:00:00Z", new BigDecimal("0.5")
    );

    private final List<String> defaultScooterForbidden = List.of(
            "2000-01-01T09:00:00Z",
            "2000-01-01T10:00:00Z",
            "2000-01-01T11:00:00Z"
    );

    private final Map<String, BigDecimal> defaultCityBikeBaseFees = Map.of(
            "Tallinn", new BigDecimal("3"),
            "Tartu", new BigDecimal("2.5"),
            "Pärnu", new BigDecimal("2")
    );

    private final Map<String, BigDecimal> defaultBikeWeatherFees = Map.of(
            "2000-01-01T01:00:00Z", new BigDecimal("0"),
            "2000-01-01T02:00:00Z", new BigDecimal("1"),
            "2000-01-01T03:00:00Z", new BigDecimal("0.5"),
            "2000-01-01T04:00:00Z", new BigDecimal("0.5"),
            "2000-01-01T06:00:00Z", new BigDecimal("1"),
            "2000-01-01T07:00:00Z", new BigDecimal("1"),
            "2000-01-01T08:00:00Z", new BigDecimal("0.5")
    );

    private final List<String> defaultBikeForbidden = List.of(
            "2000-01-01T05:00:00Z",
            "2000-01-01T09:00:00Z",
            "2000-01-01T10:00:00Z",
            "2000-01-01T11:00:00Z"
    );

    @Test
    void getDefaultFeesCarTest() throws Exception {
        String method = "car";
        OffsetDateTime time = OffsetDateTime.parse("2000-01-01T01:00:00Z");

        for (String city : defaultCityCarFees.keySet()) {
            BigDecimal carFee = defaultCityCarFees.get(city);
            DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                    .city(city).method(method).time(time).build();

            mvc.perform(get("/fee")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(feeRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.fee").value(carFee.doubleValue()));

        }
    }

    @Test
    void getDefaultFeesScooterTest() throws Exception {
        String method = "scooter";

        for (String city : defaultCityScooterBaseFees.keySet()) {
            for (String time : defaultScooterWeatherFees.keySet()) {
                BigDecimal baseFee = defaultCityScooterBaseFees.get(city);
                BigDecimal weatherFee = defaultScooterWeatherFees.get(time);
                DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                        .city(city).method(method).time(OffsetDateTime.parse(time)).build();

                mvc.perform(get("/fee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(feeRequestDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.fee").value(baseFee.add(weatherFee).doubleValue()));

            }
        }
    }

    @Test
    void getDefaultForbiddenScooterTest() throws Exception {
        String method = "scooter";

        for (String city : defaultCityScooterBaseFees.keySet()) {
            for (String time : defaultScooterForbidden) {
                DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                        .city(city).method(method).time(OffsetDateTime.parse(time)).build();

                mvc.perform(get("/fee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(feeRequestDto)))
                        .andExpect(status().is4xxClientError())
                        .andExpect(jsonPath("$.message").value("Usage of selected vehicle type is forbidden"));

            }
        }
    }

    @Test
    void getDefaultFeesBikeTest() throws Exception {
        String method = "bike";

        for (String city : defaultCityBikeBaseFees.keySet()) {
            for (String time : defaultBikeWeatherFees.keySet()) {
                BigDecimal baseFee = defaultCityBikeBaseFees.get(city);
                BigDecimal weatherFee = defaultBikeWeatherFees.get(time);
                DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                        .city(city).method(method).time(OffsetDateTime.parse(time)).build();

                mvc.perform(get("/fee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(feeRequestDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.fee").value(baseFee.add(weatherFee).doubleValue()));

            }
        }
    }

    @Test
    void getDefaultForbiddenBikeTest() throws Exception {
        String method = "bike";

        for (String city : defaultCityBikeBaseFees.keySet()) {
            for (String time : defaultBikeForbidden) {
                DeliveryFeeRequestDto feeRequestDto = DeliveryFeeRequestDto.builder()
                        .city(city).method(method).time(OffsetDateTime.parse(time)).build();

                mvc.perform(get("/fee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(feeRequestDto)))
                        .andExpect(status().is4xxClientError())
                        .andExpect(jsonPath("$.message").value("Usage of selected vehicle type is forbidden"));

            }
        }
    }

}