package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WeatherReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn
    private WeatherStation weatherStation;
    private String stationName;
    private String wmoCode;
    private Double airTemperature;
    private Double windSpeed;
    @Column(length = 50)
    private String phenomenon;
    @Column(nullable = false)
    private LocalDateTime timestamp;

}
