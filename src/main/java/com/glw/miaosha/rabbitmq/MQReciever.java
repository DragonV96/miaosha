package com.glw.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 队列消费者
 * Create by glw
 * 2019/5/30 23:46
 */
@Service
public class MQReciever {

    private static Logger logger = LoggerFactory.getLogger(MQReciever.class);

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive (String message) {
        logger.info("receive message：" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1 (String message) {
        logger.info("receive topic queue1 message：" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2 (String message) {
        logger.info("receive topic queue2 message：" + message);
    }

    @RabbitListener(queues = MQConfig.HEADERQUEUE)
    public void receiveHeader (byte[] message) {
        logger.info("receive header queue message：" + new String(message));
    }
}
