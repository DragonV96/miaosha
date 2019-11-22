package com.glw.miaosha.access;

import com.glw.miaosha.dao.doman.MsUser;

/**
 * 管理用户
 * Create by glw
 * 2019/6/3 17:20
 */
public class UserContext {
    private static ThreadLocal<MsUser> userHolder = new ThreadLocal<MsUser>();

    public static void setUser(MsUser user) {
        userHolder.set(user);
    }

    public static MsUser getUser() {
        return userHolder.get();
    }
}
