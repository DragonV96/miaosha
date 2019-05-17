package com.glw.miaosha.service;

import com.glw.miaosha.dao.OrderDao;
import com.glw.miaosha.doman.MsOrder;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.doman.OrderInfo;
import com.glw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Create by glw
 * 2019/5/16 23:12
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public MsOrder getMsOrderByUserIdGoodsId(long userId, long goodsId){
        return orderDao.getMsOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MsUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateTime(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setOrderChannel((byte)1);
        orderInfo.setStatus((byte)0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);

        MsOrder msOrder = new MsOrder();
        msOrder.setGoodsId(goods.getId());
        msOrder.setOrderId(orderId);
        msOrder.setUserId(user.getId());
        orderDao.insertMsOrder(msOrder);

        return orderInfo;
    }
}
