package com.glw.miaosha.redis;

/**
 * redis缓存 --商品key
 * Create by glw
 * 2019/5/20 9:14
 */
public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");           // 商品列表
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");         // 商品详情
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");    // 秒杀商品库存

}
