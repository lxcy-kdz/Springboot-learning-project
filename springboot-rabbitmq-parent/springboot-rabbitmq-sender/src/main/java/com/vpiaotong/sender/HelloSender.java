package com.vpiaotong.sender;

import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ReturnCallback;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author kongdezhi
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    final ConfirmCallback confirmCallback = (correlationData, ack) -> {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        if (!ack) {
            System.err.println("异常处理....");
        }
    };

    /**
     * 回调函数: return返回
     */
    final ReturnCallback returnCallback = (message) -> System.err.println(""+message);


    public void send() {
        String sendMsg = "hello " + new Date();
        System.out.println("消息正在发送Sender1 : " + sendMsg);
        rabbitTemplate.convertAndSend("topicExchange", "topic.unique.key", sendMsg);
    }
}
