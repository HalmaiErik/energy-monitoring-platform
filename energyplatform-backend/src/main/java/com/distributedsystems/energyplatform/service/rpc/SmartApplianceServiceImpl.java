package com.distributedsystems.energyplatform.service.rpc;

import com.distributedsystems.energyplatform.entity.Device;
import com.distributedsystems.energyplatform.entity.Sensor;
import com.distributedsystems.energyplatform.entity.SensorLog;
import com.distributedsystems.energyplatform.entity.User;
import com.distributedsystems.energyplatform.service.UserService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AutoJsonRpcServiceImpl
@AllArgsConstructor
public class SmartApplianceServiceImpl implements SmartApplianceService {

    private final UserService userService;

    @Override
    public List<String> getDevicesOfClient(String username) {
        User user = userService.getUserByUsername(username);
        return user.getDevices().stream().map(Device::getDescription).collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getUserHourlyConsumptionOverDays(String username, int days) {
        User user = userService.getUserByUsername(username);
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        Map<String, Double> hourlyConsumption = new TreeMap<>();
        for (Device device : user.getDevices()) {
            Sensor sensor = device.getSensor();
            for (SensorLog log : sensor.getSensorLogs()) {
                if (log.getTimeStamp().isAfter(startDate)) {
                    hourlyConsumption.merge(
                            String.valueOf(log.getTimeStamp().getHour()),
                            log.getEnergyConsumption(),
                            Double::sum
                    );
                }
            }
        }

        return hourlyConsumption;
    }

    @Override
    public Map<String, Double> getBaseline(String username) {
        Map<String, Double> baseline = getUserHourlyConsumptionOverDays(username, 7);
        baseline.replaceAll((k, v) -> baseline.get(k) / 7);
        return baseline;
    }

    @Override
    public Map<String, Map<String, Double>> getBestStartTime(String username, int hourDuration) {
        User user = userService.getUserByUsername(username);
        Map<String, Double> baseline = getBaseline(username);

        double minSum = 0.0;
        Map<String, Map<String, Double>> startTimeResults = new HashMap<>();
        for (Device device : user.getDevices()) {
            int startHour = 0;
            for (int i = 0; i < hourDuration; i++) {
                minSum += baseline.get(String.valueOf(i));
            }

            double windowSum = minSum;
            for (int i = hourDuration; i < 24; i++) {
                windowSum += baseline.get(String.valueOf(i)) - baseline.get(String.valueOf(i - hourDuration));
                if (windowSum < minSum) {
                    minSum = windowSum;
                    startHour = i - hourDuration;
                }
            }

            double deviceMaxConsumption = device.getMaxEnergyConsumption();
            Map<String, Double> estimatedConsumption = new TreeMap<>();
            for (int i = startHour; i < startHour + hourDuration; i++) {
                estimatedConsumption.put(String.valueOf(i), baseline.get(String.valueOf(i)) + deviceMaxConsumption);
            }
            startTimeResults.put(device.getDescription(), estimatedConsumption);
        }

        return startTimeResults;
    }
}
