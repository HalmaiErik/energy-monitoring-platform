package com.distributedsystems.energyplatform.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String address;
    private double maxEnergyConsumption;
    private double avgEnergyConsumption;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "device", cascade = CascadeType.REMOVE)
    private Sensor sensor;
}
