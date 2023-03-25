package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class RegionalBaseFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private DeliveryMethod deliveryMethod;
    @ManyToOne
    @JoinColumn
    private City city;
    @Column
    private BigDecimal fee;
}
