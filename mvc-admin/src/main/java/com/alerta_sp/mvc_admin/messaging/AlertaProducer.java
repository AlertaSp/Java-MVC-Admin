package com.alerta_sp.mvc_admin.messaging;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlertaProducer {

    private final RabbitTemplate rabbitTemplate;

    public AlertaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarAlerta(AlertaDTO alertaDTO) {
        rabbitTemplate.convertAndSend("alerta.fila", alertaDTO);
    }
}
