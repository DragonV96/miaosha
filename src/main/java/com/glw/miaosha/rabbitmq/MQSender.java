package com.glw.miaosha.rabbitmq;

import com.glw.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 队列生产者
 * Create by glw
 * 2019/5/30 23:45
 */
@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = RedisService.beanToString(message);
        logger.info("send message：" + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }
}
