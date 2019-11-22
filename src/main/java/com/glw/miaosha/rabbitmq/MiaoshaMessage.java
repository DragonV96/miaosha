package com.glw.miaosha.rabbitmq;

import com.glw.miaosha.dao.doman.MsUser;

/**
 * @author glw
 * @date 2019/5/31 11:08
 */
public class MiaoshaMessage {

    private MsUser msUser;
    private long goodsId;

    public MsUser getMsUser() {
        return msUser;
    }

    public void setMsUser(MsUser msUser) {
        this.msUser = msUser;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
