package com.glw.miaosha.rabbitmq;

import com.glw.miaosha.doman.MsOrder;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.service.GoodsService;
import com.glw.miaosha.service.MiaoshaService;
import com.glw.miaosha.service.OrderService;
import com.glw.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 队列消费者
 * Create by glw
 * 2019/5/30 23:46
 */
@Service
public class MQReciever {

    private static Logger logger = LoggerFactory.getLogger(MQReciever.class);

    @Autowired
    public RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaoshaMessage (String message) {
        logger.info("receive message：" + message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MsUser user = miaoshaMessage.getMsUser();
        long goodsId = miaoshaMessage.getGoodsId();

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return;
        }

        // 判断是否已经秒杀到
        MsOrder msOrder = orderService.getMsOrderByUserIdGoodsId(user.getId(), goodsId);
        if (msOrder != null) {
            return;
        }
        // 减库存->下订单->写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }


    //===================================== 练习demo =====================================

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
