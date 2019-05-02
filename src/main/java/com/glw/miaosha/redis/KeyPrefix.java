package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/1 16:20
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
