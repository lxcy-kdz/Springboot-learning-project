package com.vpiaotong.util;

import com.vpiaotong.receiver.HelloReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author kongdezhi
 */
public class MessageHelper {

    protected static final Logger LOGGER = LoggerFactory.getLogger(HelloReceiver.class);

    public static Message objToMsg(Object obj) {
        if (null == obj) {
            return null;
        }
        Message message = MessageBuilder.withBody(JsonUtil.objToStr(obj).getBytes()).build();
        // 消息持久化
        MessageProperties messageProperties = message.getMessageProperties();
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        Enumeration names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement().toString();
            String value = request.getHeader(name);
            messageProperties.setHeader(name, value);
        }
        return message;
    }

    public static <T> T msgToObj(Message message, Class<T> clazz) {
        if (null == message || null == clazz) {
            return null;
        }
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            LOGGER.debug("key:{},value:{}", key, value);
        }
        String str = new String(message.getBody());
        T obj = JsonUtil.strToObj(str, clazz);
        return obj;
    }

}
