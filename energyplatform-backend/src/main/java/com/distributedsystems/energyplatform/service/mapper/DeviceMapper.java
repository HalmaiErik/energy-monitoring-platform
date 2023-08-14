package com.distributedsystems.energyplatform.service.mapper;

import com.distributedsystems.energyplatform.dto.DeviceDto;
import com.distributedsystems.energyplatform.entity.Device;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeviceMapper {

    private final ModelMapper modelMapper;

    public Device mapToEntity(DeviceDto deviceDto) {
        return modelMapper.map(deviceDto, Device.class);
    }

    public DeviceDto mapToDto(Device device) {
        return modelMapper.map(device, DeviceDto.class);
    }
}
