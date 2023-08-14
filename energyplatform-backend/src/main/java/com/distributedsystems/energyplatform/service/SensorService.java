package com.distributedsystems.energyplatform.service;

import com.distributedsystems.energyplatform.dto.SensorDto;
import com.distributedsystems.energyplatform.dto.SensorEditDto;
import com.distributedsystems.energyplatform.entity.Sensor;
import com.distributedsystems.energyplatform.entity.SensorLog;
import com.distributedsystems.energyplatform.exception.EditUncreatedEntityException;
import com.distributedsystems.energyplatform.repository.SensorRepository;
import com.distributedsystems.energyplatform.service.mapper.SensorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;
    private final DeviceService deviceService;

    public Long insertSensor(SensorDto sensorDto) {
        Sensor savedSensor = sensorRepository.save(sensorMapper.mapToEntity(sensorDto));
        return savedSensor.getId();
    }

    public Long editSensor(SensorEditDto sensorEditDto) {
        if (sensorRepository.existsById(sensorEditDto.getId())) {
            Sensor sensor = sensorRepository.getById(sensorEditDto.getId());
            sensor.setDescription(sensorEditDto.getDescription());
            sensor.setDevice(deviceService.findDeviceById(sensorEditDto.getIdDevice()));
            sensorRepository.save(sensor);
            return sensor.getId();
        }
        throw new EditUncreatedEntityException("Sensor with id " + sensorEditDto.getId() + " does not exist!");
    }

    public Long deleteSensor(Long id) {
        sensorRepository.deleteById(id);
        return id;
    }

    public SensorDto findSensorOfDevice(Long idDevice) {
        Sensor sensor = sensorRepository.findByDevice_Id(idDevice);
        return sensorMapper.mapToDto(sensor);
    }

    public void updateConsumption(SensorLog sensorLog) {
        Sensor sensor = getSensorById(sensorLog.getSensor().getId());
        if (sensorLog.getEnergyConsumption() > sensor.getMaxValue()) {
            sensor.setMaxValue(sensorLog.getEnergyConsumption());
            sensorRepository.save(sensor);
        }
        deviceService.updateAvgConsumptionOfDevice(sensor.getDevice(), sensor.getSensorLogs());
    }

    public Sensor getSensorById(Long id) {
        return sensorRepository.getById(id);
    }
}
