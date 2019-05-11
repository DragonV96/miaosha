package com.glw.miaosha.util;

import java.util.UUID;

/**
 * Create by glw
 * 2019/5/10 23:20
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
