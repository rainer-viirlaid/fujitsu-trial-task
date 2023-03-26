package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String cityName;
    @ManyToOne
    @JoinColumn(nullable = false)
    private WeatherStation weatherStation;

    @Override
    public String toString() {
        return "City " + cityName + ", served by " + weatherStation.getStationName() + " weather station.";
    }
}
