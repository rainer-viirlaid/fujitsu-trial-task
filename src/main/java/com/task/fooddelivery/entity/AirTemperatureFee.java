package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AirTemperatureFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double minTemp;
    private Double maxTemp;
    @Column(nullable = false)
    private boolean deliveryForbidden;
    @ManyToOne
    @JoinColumn(nullable = false)
    private DeliveryMethod deliveryMethod;
    @Column(nullable = false)
    private BigDecimal fee;

    public Double getMinTempNotNull() {
        return minTemp == null ? Double.MIN_VALUE : minTemp;
    }

    public double getMaxTempNotNull() {
        return maxTemp == null ? Double.MAX_VALUE : maxTemp;
    }
}
