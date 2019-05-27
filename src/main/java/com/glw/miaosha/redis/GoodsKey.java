package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/20 9:14
 */
public class GoodsKey extends BasePrefix {
    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey("gl");
    public static GoodsKey getGoodsDetail = new GoodsKey("gd");

}
