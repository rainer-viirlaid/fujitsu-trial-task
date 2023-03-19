package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
    @Column
    private String methodName;
}
