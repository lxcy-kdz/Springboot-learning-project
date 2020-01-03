package com.vpiaotong.service.impl;

import com.vpiaotong.service.MassageSendService;
import com.vpiaotong.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kongdezhi
 */
@Service
public class MassageSendServiceImpl implements MassageSendService {

    protected final Logger LOGGER = LoggerFactory.getLogger(MassageSendServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String sendMassage(String massage) {
        LOGGER.info("ces");
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                String msgId = correlationData.getId();
                LOGGER.info("发送到Executor成功!信息id为:{}", msgId);
            } else {
                LOGGER.info("发送到Executor失败!");
            }
        });
        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            LOGGER.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });
        Message message = MessageHelper.objToMsg(massage);
        CorrelationData correlationData = new CorrelationData("123123123");
        rabbitTemplate.convertAndSend("topicExchange", "topic.unique.key", message,correlationData);
        return "SUCCESS";
    }
}
