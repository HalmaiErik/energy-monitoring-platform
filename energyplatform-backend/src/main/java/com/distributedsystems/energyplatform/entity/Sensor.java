package com.distributedsystems.energyplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double maxValue;
    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE)
    private List<SensorLog> sensorLogs;
}
