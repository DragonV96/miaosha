package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/1 16:44
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {    // 永不过期
        super(prefix);
    }

    public static OrderKey getMsOrderByUidGid = new OrderKey("moug");
}
