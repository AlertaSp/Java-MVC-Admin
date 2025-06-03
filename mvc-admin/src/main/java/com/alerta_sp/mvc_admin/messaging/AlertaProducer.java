package com.alerta_sp.mvc_admin.messaging;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlertaProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public AlertaProducer(RabbitTemplate rabbitTemplate,
                          @Value("${rabbitmq.exchange}") String exchange,
                          @Value("${rabbitmq.routingkey}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void enviarAlerta(AlertaDTO dto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, dto);
        System.out.println("🚀 Alerta enviado via RabbitMQ: " + dto.getMensagem());
    }
}
