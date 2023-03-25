package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class DeliveryMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, length = 50)
    private String methodName;
}
