package com.distributedsystems.energyplatform.service.websocket;

import com.distributedsystems.energyplatform.dto.LogAlertDto;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientAlerter {

    private SimpMessagingTemplate template;

    public void alert(LogAlertDto logAlertDto) {
        this.template.convertAndSend("/log-alert/get", logAlertDto, new MessagePostProcessor() {
            @Override
            public Message<?> postProcessMessage(Message<?> message) {
                byte[] byteStr = message.getPayload().toString().getBytes();
                String msg = new String(byteStr);
                return message;
            }
        });
    }

}
