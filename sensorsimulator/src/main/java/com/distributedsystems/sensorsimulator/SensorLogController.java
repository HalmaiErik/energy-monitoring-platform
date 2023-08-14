package com.distributedsystems.sensorsimulator;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/log")
@AllArgsConstructor
public class SensorLogController {

    private final ConsumptionReader consumptionReader;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/{sensorId}")
    public void sendLog(@PathVariable Long sensorId) throws InterruptedException {
        List<Double> values = consumptionReader.readValues();
        for (Double value : values) {
            SensorLog log = new SensorLog();
            log.setEnergyConsumption(value);
            log.setTimeStamp(LocalDateTime.now());
            log.setIdSensor(sensorId);

            System.out.println("Sending: " + log.toString());
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, log);
            TimeUnit.SECONDS.sleep(15);
        }
    }

}
