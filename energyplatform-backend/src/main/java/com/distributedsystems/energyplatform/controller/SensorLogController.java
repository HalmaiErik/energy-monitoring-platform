package com.distributedsystems.energyplatform.controller;

import com.distributedsystems.energyplatform.dto.SensorLogDto;
import com.distributedsystems.energyplatform.service.SensorLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class SensorLogController {

    private final SensorLogService sensorLogService;

    @PostMapping("/api/new/sensorlog")
    public ResponseEntity<Long> insertSensorLog(@RequestBody SensorLogDto sensorLogDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sensorLogService.processSensorLog(sensorLogDto));
    }

    @GetMapping("/api/view/sensorlogs/by-sensor/{id}")
    public ResponseEntity<List<SensorLogDto>> findSensorOfDevice(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sensorLogService.findLogsForSensor(id));
    }
}
