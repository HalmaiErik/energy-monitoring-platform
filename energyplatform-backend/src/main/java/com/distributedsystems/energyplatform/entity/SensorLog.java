package com.distributedsystems.energyplatform.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensorlog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SensorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeStamp;
    private double energyConsumption;
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
}
