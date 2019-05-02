package com.glw.miaosha.redis;

/**
 * Create by glw
 * 2019/5/1 16:43
 */
public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
