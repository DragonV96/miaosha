package com.glw.miaosha.service;

import com.glw.miaosha.dao.GoodsDao;
import com.glw.miaosha.doman.Goods;
import com.glw.miaosha.doman.MsOrder;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.doman.OrderInfo;
import com.glw.miaosha.redis.MiaoshaKey;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by glw
 * 2019/5/16 23:18
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MsUser user, GoodsVo goods) {
        // 减库存->下订单->写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            // order_info ms_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MsOrder order = orderService.getMsOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {    // 秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {   // 没抢到
                return -1;
            } else {        // 抢到了
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }


}
