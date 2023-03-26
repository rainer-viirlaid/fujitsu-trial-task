package com.task.fooddelivery.controller.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WeatherStationControllerTest {

    @Autowired
    private MockMvc mvc;

    private final List<String> defaultWeatherStations = List.of(
            "Tallinn-Harku",
            "Tartu-Tõravere",
            "Pärnu"
    );

    @Test
    public void getWeatherStationDefaultsTest() throws Exception {
        for (String station : defaultWeatherStations) {
            mvc.perform(get("/business/weather_station/read").param("station", station))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(true));
        }
    }

    @Test
    public void createWeatherStationTest() throws Exception {
        String station = "createStationTest";
        mvc.perform(get("/business/weather_station/read").param("station", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
        mvc.perform(post("/business/weather_station/create").param("station", station))
                .andExpect(status().isOk());
        mvc.perform(get("/business/weather_station/read").param("station", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void updateWeatherStationTest() throws Exception {
        String station = "updateStationTest";
        String stationNew = "updateStationTestRenamed";
        mvc.perform(put("/business/weather_station/update").param("old_name", station).param("new_name", stationNew))
                .andExpect(status().isOk());
        mvc.perform(get("/business/weather_station/read").param("station", stationNew))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void deleteWeatherStationTest() throws Exception {
        String station = "deleteStationTest";
        mvc.perform(delete("/business/weather_station/delete").param("station", station))
                .andExpect(status().isOk());
        mvc.perform(get("/business/weather_station/read").param("station", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void deleteWeatherStationDeletesReportsTest() throws Exception {
        String station = "deleteStationDeletesReportsTest";
        mvc.perform(delete("/business/weather_station/delete").param("station", station))
                .andExpect(status().isOk());
        mvc.perform(get("/business/weather_station/read").param("station", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void cannotDeleteStationBeforeCityTest() throws Exception {
        String station = defaultWeatherStations.get(0);
        mvc.perform(delete("/business/weather_station/delete").param("station", station))
                .andExpect(status().is5xxServerError());
        mvc.perform(get("/business/weather_station/read").param("station", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}