package com.glw.miaosha.vo;

import com.glw.miaosha.doman.OrderInfo;

/**
 * @author glw
 * @date 2019/5/30 15:34
 */
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
