package com.distributedsystems.energyplatform.service.mapper;

import com.distributedsystems.energyplatform.dto.SensorDto;
import com.distributedsystems.energyplatform.entity.Sensor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SensorMapper {

    private final ModelMapper modelMapper;

    public Sensor mapToEntity(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }

    public SensorDto mapToDto(Sensor sensor) {
        try {
            return modelMapper.map(sensor, SensorDto.class);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}
