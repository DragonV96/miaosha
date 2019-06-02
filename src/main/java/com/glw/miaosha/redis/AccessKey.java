package com.glw.miaosha.redis;

/**
 * @author glw
 * @date 2019/5/31 15:16
 */
public class AccessKey extends BasePrefix{

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey access = new AccessKey(5, "access");
}
