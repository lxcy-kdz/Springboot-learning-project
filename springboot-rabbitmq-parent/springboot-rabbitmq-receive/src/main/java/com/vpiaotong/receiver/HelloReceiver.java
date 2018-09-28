package com.vpiaotong.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "commonQueue")
public class HelloReceiver {

    @RabbitHandler
    public void receiver(String hello){
        System.out.println("接收到消息===============================：" + hello);
        System.out.println("");
    }
}
