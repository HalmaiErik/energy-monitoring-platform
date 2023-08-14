package com.distributedsystems.energyplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {

    private Long id;
    private String description;
    private String address;
    private double maxEnergyConsumption;
    private double avgEnergyConsumption;
    private Long idUser;
}
