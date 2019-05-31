package com.glw.miaosha.redis;

/**
 * @author glw
 * @date 2019/5/31 15:16
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
