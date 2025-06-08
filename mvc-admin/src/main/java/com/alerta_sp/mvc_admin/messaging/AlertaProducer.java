package com.alerta_sp.mvc_admin.messaging;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.MensagemAlertaDTO;
import com.alerta_sp.mvc_admin.repository.CorregoRepository;
import com.alerta_sp.mvc_admin.model.Corrego;
import com.alerta_sp.mvc_admin.model.TipoAlerta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlertaProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;
    private final CorregoRepository corregoRepository;

    public AlertaProducer(RabbitTemplate rabbitTemplate,
                          @Value("${rabbitmq.exchange}") String exchange,
                          @Value("${rabbitmq.routingkey}") String routingKey,
                          CorregoRepository corregoRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.corregoRepository = corregoRepository;
    }

    public void enviarAlerta(AlertaDTO dto) {
        if (dto.getCorrego() == null || dto.getCorrego().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Nome do córrego não pode ser nulo ou vazio ao enviar alerta.");
        }

        MensagemAlertaDTO mensagemDTO = new MensagemAlertaDTO(
                dto.getMensagem(),
                dto.getNivel(),
                dto.getIdCorrego()
        );

        // Garante que o campo 'tipo' esteja sempre definido
        try {
            String tipo = TipoAlerta.valueOf(dto.getNivel()).name();
            mensagemDTO.setTipo(tipo);
        } catch (Exception ex) {
            mensagemDTO.setTipo("INDEFINIDO");
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, mensagemDTO);
        System.out.println("🚀 Alerta enviado via RabbitMQ: " + mensagemDTO);
    }

}
