package com.task.fooddelivery.controller.business;

import com.task.fooddelivery.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class CityController {

    private final CityService cityService;

    /**
     * Save a new city into the database.
     *
     * @param cityName name of the city
     * @param stationName name of the associated weather station
     */
    @PostMapping("/business/city/create")
    public void addCity(@RequestParam("city") String cityName, @RequestParam("station") String stationName) {
        cityService.addCity(cityName, stationName);
    }

    /**
     * Get information about a city.
     *
     * @param cityName name of the city
     * @return name of the city and the associated weather station
     */
    @GetMapping("/business/city/read")
    public String getCity(@RequestParam("city") String cityName) {
        return cityService.getCityInformation(cityName);
    }

    /**
     * Change the name or weather station of a city.
     *
     * @param oldName old city name
     * @param newName new city name
     * @param stationName weather station name
     */
    @PutMapping("/business/city/update")
    public void updateCityName(@RequestParam("old_name") String oldName, @RequestParam("new_name") String newName, @RequestParam("station") String stationName) {
        cityService.updateCity(oldName, newName, stationName);
    }

    /**
     * Delete a city from the database.
     * <p>
     * Any related regional base fees must be deleted beforehand.
     *
     * @param cityName name of the city
     */
    @DeleteMapping("/business/city/delete")
    public void deleteCity(@RequestParam("city") String cityName) {
        cityService.deleteCity(cityName);
    }
}
