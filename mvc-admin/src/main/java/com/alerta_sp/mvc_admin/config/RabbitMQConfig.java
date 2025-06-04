package com.alerta_sp.mvc_admin.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ALERTA_QUEUE = "alertas.queue";
    public static final String ALERTA_EXCHANGE = "alertas.exchange";
    public static final String ALERTA_ROUTING_KEY = "alertas.routingkey";

    @Bean
    public Queue alertaQueue() {
        // durable = true, exclusive = false, autoDelete = false
        return QueueBuilder.durable(ALERTA_QUEUE).build();
    }

    @Bean
    public DirectExchange alertaExchange() {
        return new DirectExchange(ALERTA_EXCHANGE);
    }

    @Bean
    public Binding alertaBinding(Queue alertaQueue, DirectExchange alertaExchange) {
        return BindingBuilder.bind(alertaQueue).to(alertaExchange).with(ALERTA_ROUTING_KEY);
    }

    @Bean
    public Queue healthCheckQueue() {
        // Health check queue não precisa ser durável
        return new Queue("health-check-queue", false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
