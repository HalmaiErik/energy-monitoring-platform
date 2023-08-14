package com.distributedsystems.energyplatform.service.mapper;

import com.distributedsystems.energyplatform.dto.SensorLogDto;
import com.distributedsystems.energyplatform.entity.SensorLog;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SensorLogMapper {

    private final ModelMapper modelMapper;

    public SensorLog mapToEntity(SensorLogDto sensorLogDto) {
        return modelMapper.map(sensorLogDto, SensorLog.class);
    }

    public SensorLogDto mapToDto(SensorLog sensorLog) {
        return modelMapper.map(sensorLog, SensorLogDto.class);
    }
}
