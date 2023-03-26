package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.service.WeatherStationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class WeatherStationController {

    private final WeatherStationService stationService;

    /**
     * Save a new weather station into the database.
     *
     * @param stationName name of the weather station
     */
    @PostMapping("/business/weather_station/create")
    public void createWeatherStation(@RequestParam("station") String stationName) {
        stationService.addWeatherStation(stationName);
    }

    /**
     * Check if the weather station already exists in the database.
     *
     * @param stationName name of the weather station
     * @return whether the weather station exists in the database
     */
    @GetMapping("/business/weather_station/read")
    public boolean getWeatherStation(@RequestParam("station") String stationName) {
        return stationService.doesWeatherStationExist(stationName);
    }

    /**
     * Change the name of a weather station.
     *
     * @param oldName old weather station name
     * @param newName new weather station name
     */
    @PutMapping("/business/weather_station/update")
    public void updateWeatherStation(@RequestParam("old_name") String oldName, @RequestParam("new_name") String newName) {
        stationService.updateWeatherStationName(oldName, newName);
    }

    /**
     * Delete a weather station from the database.
     * <p>
     * Any related cities must be deleted or updated beforehand. Any related weather reports will be deleted.
     *
     * @param stationName name of the delivery method
     */
    @DeleteMapping("/business/weather_station/delete")
    public void deleteWeatherStation(@RequestParam("station") String stationName) {
        stationService.deleteWeatherStation(stationName);
    }
}
