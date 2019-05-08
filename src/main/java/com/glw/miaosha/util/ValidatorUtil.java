package com.glw.miaosha.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author glw
 * @date 2019/5/8 10:31
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    /**
     * 判断是否为正确的手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println("手机号：18912341234");
        System.out.println(isMobile("18912341234"));
        System.out.println(isMobile("1891234123"));
    }
}
