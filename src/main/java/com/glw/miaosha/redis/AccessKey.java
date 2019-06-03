package com.glw.miaosha.redis;

/**
 * 权限键类
 * @author glw
 * @date 2019/5/31 15:16
 */
public class AccessKey extends BasePrefix{

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey updateKey(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
