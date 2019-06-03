package com.glw.miaosha.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求接口限流注解接口
 * Create by glw
 * 2019/6/3 16:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();      // 限制秒数
    int maxCoutn();     // 限制最大请求数
    boolean needLogin() default true;
}
