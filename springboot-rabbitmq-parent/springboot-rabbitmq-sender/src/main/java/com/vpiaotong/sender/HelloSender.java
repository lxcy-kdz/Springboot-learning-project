package com.vpiaotong.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String sendMsg = "hello " + new Date();
        System.out.println("消息正在发送Sender1 : " + sendMsg);
        rabbitTemplate.convertAndSend("topicExchange", "topic.unique.key", sendMsg);
    }
}
