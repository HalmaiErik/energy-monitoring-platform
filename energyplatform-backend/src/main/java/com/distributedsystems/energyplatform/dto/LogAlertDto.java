package com.distributedsystems.energyplatform.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogAlertDto {

    private String username;
    private String deviceName;
    private double energyConsumption;
}
