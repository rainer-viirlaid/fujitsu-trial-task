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
public class PhenomenonFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 100)
    private String phenomenonName;
    @Column(nullable = false)
    private boolean deliveryForbidden;
    @ManyToOne
    @JoinColumn(nullable = false)
    private DeliveryMethod deliveryMethod;
    @Column(nullable = false)
    private BigDecimal fee;
}
