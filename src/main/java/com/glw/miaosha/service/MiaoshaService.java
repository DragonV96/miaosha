package com.glw.miaosha.service;

import com.glw.miaosha.dao.GoodsDao;
import com.glw.miaosha.doman.Goods;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.doman.OrderInfo;
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

    @Transactional
    public OrderInfo miaosha(MsUser user, GoodsVo goods) {
        // 减库存->下订单->写入秒杀订单
        goodsService.reduceStock(goods);
        // order_info ms_order
        OrderInfo orderInfo = orderService.createOrder(user, goods);
        return orderInfo;
    }
}
