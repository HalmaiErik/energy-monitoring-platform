package com.distributedsystems.energyplatform.service;

import com.distributedsystems.energyplatform.dto.DeviceDto;
import com.distributedsystems.energyplatform.dto.DeviceEditDto;
import com.distributedsystems.energyplatform.entity.Device;
import com.distributedsystems.energyplatform.entity.SensorLog;
import com.distributedsystems.energyplatform.exception.EditUncreatedEntityException;
import com.distributedsystems.energyplatform.repository.DeviceRepository;
import com.distributedsystems.energyplatform.service.mapper.DeviceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final UserService userService;

    public Long insertDevice(DeviceDto deviceDto) {
        Device savedDevice = deviceRepository.save(deviceMapper.mapToEntity(deviceDto));
        return savedDevice.getId();
    }

    public Long editDevice(DeviceEditDto deviceEditDto) {
        if (deviceRepository.existsById(deviceEditDto.getId())) {
            Device device = deviceRepository.getById(deviceEditDto.getId());
            device.setDescription(deviceEditDto.getDescription());
            device.setAddress(deviceEditDto.getAddress());
            device.setMaxEnergyConsumption(deviceEditDto.getMaxEnergyConsumption());
            device.setUser(userService.getUserByUsername(deviceEditDto.getClientUsername()));
            deviceRepository.save(device);
            return device.getId();
        }
        throw new EditUncreatedEntityException("Device with id " + deviceEditDto.getId() + " does not exit!");
    }

    public Long deleteDevice(Long id) {
        deviceRepository.deleteById(id);
        return id;
    }

    public List<DeviceDto> findDevicesOfClient(Long idClient) {
        List<Device> devices = deviceRepository.findAllByUser_Id(idClient);
        return devices.stream()
                .map(deviceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<DeviceDto> findAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(deviceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Device findDeviceById(Long id) {
        return deviceRepository.getById(id);
    }

    public void updateAvgConsumptionOfDevice(Device device, List<SensorLog> logs) {
        double sum = 0.0;
        for (SensorLog log : logs) {
            sum += log.getEnergyConsumption();
        }
        device.setAvgEnergyConsumption(sum / logs.size());
        deviceRepository.save(device);
    }
}
