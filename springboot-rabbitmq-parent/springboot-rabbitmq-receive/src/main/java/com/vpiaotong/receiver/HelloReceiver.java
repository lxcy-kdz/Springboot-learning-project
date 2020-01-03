package com.vpiaotong.receiver;

import com.rabbitmq.client.Channel;
import com.vpiaotong.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author kongdezhi
 */
@Component
public class HelloReceiver {

    protected final Logger LOGGER = LoggerFactory.getLogger(HelloReceiver.class);

    @RabbitListener(queues = "commonQueue")
    public void receiverCommonQueue(Message message, Channel channel ) throws Exception{
        String msg = MessageHelper.msgToObj(message, String.class);
        LOGGER.debug("服务接受到消息:{}",msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }

    }
}
