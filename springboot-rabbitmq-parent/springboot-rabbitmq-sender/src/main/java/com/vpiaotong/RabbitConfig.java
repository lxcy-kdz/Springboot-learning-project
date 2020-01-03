package com.vpiaotong;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxcy_
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue directQueue(){
        return new Queue("directQueue");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

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
