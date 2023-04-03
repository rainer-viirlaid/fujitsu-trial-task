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
class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    private final List<String> defaultCities = List.of(
            "Tallinn",
            "Tartu",
            "Pärnu"
    );

    @Test
    void getDefaultCitiesTest() throws Exception {
        mvc.perform(get("/business/city/read").param("city", "Tallinn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value("City Tallinn, served by Tallinn-Harku weather station."));
        for (String city : defaultCities) {
            mvc.perform(get("/business/city/read").param("city", city))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void createCityTest() throws Exception {
        String city = "createCityTest";
        String station = "Pärnu";
        mvc.perform(get("/business/city/read").param("city", city))
                .andExpect(status().is4xxClientError());
        mvc.perform(post("/business/city/create").param("city", city).param("station", station))
                .andExpect(status().isOk());
        mvc.perform(get("/business/city/read").param("city", city))
                .andExpect(status().isOk());
    }

    @Test
    void cannotCreateDuplicateCityTest() throws Exception {
        String city = "Tallinn";
        String station = "Pärnu";
        mvc.perform(post("/business/city/create").param("city", city).param("station", station))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateCityNameTest() throws Exception {
        String station = "Pärnu";
        String city = "updateCityNameTest";
        String cityNew = "updateCityNameTestRenamed";
        mvc.perform(put("/business/city/update").param("old_name", city).param("new_name", cityNew).param("station", station))
                .andExpect(status().isOk());
        mvc.perform(get("/business/city/read").param("city", cityNew))
                .andExpect(status().isOk());
    }

    @Test
    void updateCityStationTest() throws Exception {
        String stationNew = "Tallinn-Harku";
        String city = "updateCityStationTest";
        mvc.perform(put("/business/city/update").param("old_name", city).param("new_name", city).param("station", stationNew))
                .andExpect(status().isOk());
        mvc.perform(get("/business/city/read").param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value("City updateCityStationTest, served by Tallinn-Harku weather station."));
    }

    @Test
    void deleteCityTest() throws Exception {
        String city = "deleteCityTest";
        mvc.perform(delete("/business/city/delete").param("city", city))
                .andExpect(status().isOk());
        mvc.perform(get("/business/city/read").param("city", city))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void cannotDeleteCityBeforeRegionalBaseFeeTest() throws Exception {
        String city = defaultCities.get(0);
        mvc.perform(delete("/business/city/delete").param("city", city))
                .andExpect(status().is5xxServerError());
        mvc.perform(get("/business/city/read").param("city", city))
                .andExpect(status().isOk());
    }

}