package com.distributedsystems.energyplatform.controller;

import com.distributedsystems.energyplatform.dto.SensorDto;
import com.distributedsystems.energyplatform.dto.SensorEditDto;
import com.distributedsystems.energyplatform.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/api/new/sensor")
    public ResponseEntity<Long> insertSensor(@RequestBody SensorDto sensorDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sensorService.insertSensor(sensorDto));
    }

    @PostMapping("/api/edit/sensor")
    public ResponseEntity<Long> editSensor(@RequestBody SensorEditDto sensorEditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sensorService.editSensor(sensorEditDto));
    }

    @DeleteMapping("/api/delete/sensor/{id}")
    public ResponseEntity<Long> deleteSensor(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sensorService.deleteSensor(id));
    }

    @GetMapping("/api/view/sensor/by-device/{id}")
    public ResponseEntity<SensorDto> findSensorOfDevice(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sensorService.findSensorOfDevice(id));
    }
}
