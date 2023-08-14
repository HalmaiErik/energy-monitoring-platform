package com.distributedsystems.energyplatform.service;

import com.distributedsystems.energyplatform.dto.LogAlertDto;
import com.distributedsystems.energyplatform.dto.SensorLogDto;
import com.distributedsystems.energyplatform.entity.Sensor;
import com.distributedsystems.energyplatform.entity.SensorLog;
import com.distributedsystems.energyplatform.repository.SensorLogRepository;
import com.distributedsystems.energyplatform.service.mapper.SensorLogMapper;
import com.distributedsystems.energyplatform.service.websocket.ClientAlerter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SensorLogService {

    private final SensorLogRepository sensorLogRepository;
    private final SensorLogMapper sensorLogMapper;
    private final SensorService sensorService;
    private final ClientAlerter clientAlerter;

    public Long processSensorLog(SensorLogDto sensorLogDto) {
        alertIfNeeded(sensorLogDto);
        return insertSensorLog(sensorLogDto);
    }

    private void alertIfNeeded(SensorLogDto newLog) {
        SensorLog lastLog = sensorLogRepository.findFirstBySensor_IdOrderByTimeStampDesc(newLog.getIdSensor());
        if (lastLog != null) {
            System.out.println(lastLog);
            Sensor sensor = lastLog.getSensor();
            double powerDiff = newLog.getEnergyConsumption() - lastLog.getEnergyConsumption();
            long timeDiff = ChronoUnit.SECONDS.between(lastLog.getTimeStamp(), newLog.getTimeStamp());
            double powerPeak = powerDiff / timeDiff;

            if (powerPeak > sensor.getDevice().getMaxEnergyConsumption()) {
                String deviceName = sensor.getDevice().getDescription();
                String username = sensor.getDevice().getUser().getUsername();
                LogAlertDto logAlert = new LogAlertDto(username, deviceName, powerPeak);

                clientAlerter.alert(logAlert);
            }
        }
    }

    private Long insertSensorLog(SensorLogDto sensorLogDto) {
        SensorLog sensorLog = sensorLogRepository.save(sensorLogMapper.mapToEntity(sensorLogDto));
        sensorService.updateConsumption(sensorLog);
        return sensorLog.getId();
    }

    public List<SensorLogDto> findLogsForSensor(Long idSensor) {
        List<SensorLog> sensorLogs = sensorLogRepository.findAllBySensor_IdOrderByTimeStamp(idSensor);
        return sensorLogs.stream()
                .map(sensorLogMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
