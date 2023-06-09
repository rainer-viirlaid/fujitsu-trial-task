package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegionalBaseFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private DeliveryMethod deliveryMethod;
    @ManyToOne
    @JoinColumn(nullable = false)
    private City city;
    @Column(nullable = false)
    private BigDecimal fee;
}
