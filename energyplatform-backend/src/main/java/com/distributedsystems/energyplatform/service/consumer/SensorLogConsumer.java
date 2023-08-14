package com.distributedsystems.energyplatform.service.consumer;

import com.distributedsystems.energyplatform.dto.SensorLogDto;
import com.distributedsystems.energyplatform.service.SensorLogService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SensorLogConsumer {

    private final SensorLogService sensorLogService;
    private final SimpMessagingTemplate template;

    @RabbitListener(queues = "sensor_queue")
    public void consumeMessageFromQueue(SensorLogDto sensorLogDto) {
        sensorLogService.processSensorLog(sensorLogDto);
    }
}
