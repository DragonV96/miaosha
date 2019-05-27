package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/20 9:14
 */
public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");

}
