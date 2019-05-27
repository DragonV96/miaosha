package com.glw.miaosha.vo;

import com.glw.miaosha.doman.MsUser;

/**
 * Create by glw
 * 2019/5/27 21:15
 */
public class GoodsDetailVo {
    private int msStatus;
    private int remainSeconds;
    private GoodsVo goods;
    private MsUser user;

    public int getMsStatus() {
        return msStatus;
    }

    public void setMsStatus(int msStatus) {
        this.msStatus = msStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public MsUser getUser() {
        return user;
    }

    public void setUser(MsUser user) {
        this.user = user;
    }
}
