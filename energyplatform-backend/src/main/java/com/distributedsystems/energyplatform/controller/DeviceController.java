package com.distributedsystems.energyplatform.controller;

import com.distributedsystems.energyplatform.dto.DeviceDto;
import com.distributedsystems.energyplatform.dto.DeviceEditDto;
import com.distributedsystems.energyplatform.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/api/new/device")
    public ResponseEntity<Long> insertDevice(@RequestBody DeviceDto deviceDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceService.insertDevice(deviceDto));
    }

    @PostMapping("/api/edit/device")
    public ResponseEntity<Long> editDevice(@RequestBody DeviceEditDto deviceEditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceService.editDevice(deviceEditDto));
    }

    @DeleteMapping("/api/delete/device/{id}")
    public ResponseEntity<Long> deleteDevice(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deviceService.deleteDevice(id));
    }

    @GetMapping("/api/view/devices/by-client/{id}")
    public ResponseEntity<List<DeviceDto>> findDevicesOfUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deviceService.findDevicesOfClient(id));
    }

    @GetMapping("/api/view/devices")
    public ResponseEntity<List<DeviceDto>> findAllDevices() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deviceService.findAllDevices());
    }
}
