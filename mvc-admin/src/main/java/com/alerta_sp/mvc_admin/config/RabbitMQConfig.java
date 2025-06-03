package com.alerta_sp.mvc_admin.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue alertaQueue() {
        return new Queue("alertas.queue", true);
    }

    @Bean
    public DirectExchange alertaExchange() {
        return new DirectExchange("alertas.exchange");
    }

    @Bean
    public Binding alertaBinding() {
        return BindingBuilder.bind(alertaQueue()).to(alertaExchange()).with("alertas.routingkey");
    }

    @Bean
    public Queue healthCheckQueue() {
        return new Queue("health-check-queue", false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
