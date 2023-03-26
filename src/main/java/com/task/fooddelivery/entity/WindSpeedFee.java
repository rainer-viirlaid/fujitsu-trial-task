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
public class WindSpeedFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double minSpeed;
    private Double maxSpeed;
    @Column(nullable = false)
    private boolean deliveryForbidden;
    @ManyToOne
    @JoinColumn(nullable = false)
    private DeliveryMethod deliveryMethod;
    @Column(nullable = false)
    private BigDecimal fee;

    public Double getMinSpeedNotNull() {
        return minSpeed == null ? Double.MIN_VALUE : minSpeed;
    }

    public double getMaxSpeedNotNull() {
        return maxSpeed == null ? Double.MAX_VALUE : maxSpeed;
    }
}
