package com.glw.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by glw
 * 2019/5/30 23:46
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}
