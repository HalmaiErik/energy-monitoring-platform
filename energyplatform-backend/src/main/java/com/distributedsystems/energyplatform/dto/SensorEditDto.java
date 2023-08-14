package com.distributedsystems.energyplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorEditDto {

    private Long id;
    private String description;
    private Long idDevice;
}
