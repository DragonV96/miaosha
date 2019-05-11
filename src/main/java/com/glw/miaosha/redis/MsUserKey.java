package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/10 23:59
 */
public class MsUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private MsUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MsUserKey token = new MsUserKey(TOKEN_EXPIRE, "tk");

}
