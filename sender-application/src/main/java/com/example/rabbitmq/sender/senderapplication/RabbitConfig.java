package com.example.rabbitmq.sender.senderapplication;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxcy_
 */
@Configuration
public class RabbitConfig {


    @Bean
    public Queue commonQueue() {
        return new Queue("commonQueue");
    }

    @Bean
    public TopicExchange commonExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding commonBinding() {
        return BindingBuilder.bind(commonQueue()).to(commonExchange()).with("topic.unique.key");
    }
}
