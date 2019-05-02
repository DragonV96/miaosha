package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/1 16:44
 */
public class OrderKey extends BasePrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
