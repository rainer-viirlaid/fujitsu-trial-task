package com.task.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, length = 50)
    private String methodName;
    @OneToMany(mappedBy = "deliveryMethod")
    private List<PhenomenonFee> phenomenonFees;
    @OneToMany(mappedBy = "deliveryMethod")
    private List<AirTemperatureFee> airTemperatureFees;
    @OneToMany(mappedBy = "deliveryMethod")
    private List<WindSpeedFee> windSpeedFees;

    @Override
    public String toString() {
        return "Delivery method " + methodName + ".";
    }
}
