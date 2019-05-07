package com.glw.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author glw
 * @date 2019/5/7 17:08
 */
public class MD5Util {

    private static final String SALT = "f56wet15";      // 盐

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    // 密码输入端加密
    public static String inputPassFormPass(String inputPass) {
        StringBuilder resultPass = new StringBuilder();
        resultPass.append(SALT.charAt(0));
        resultPass.append(SALT.charAt(2));
        resultPass.append(inputPass);
        resultPass.append(SALT.charAt(5));
        resultPass.append(SALT.charAt(4));
        return md5(resultPass.toString());
    }

    // 存入数据库二次加密
    public static String formPassToDBPass(String formPass, String salt) {
        StringBuilder resultPass = new StringBuilder();
        resultPass.append(salt.charAt(0));
        resultPass.append(salt.charAt(2));
        resultPass.append(formPass);
        resultPass.append(salt.charAt(5));
        resultPass.append(salt.charAt(4));
        return md5(resultPass.toString());
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
//        System.out.println(inputPassFormPass("123456"));
//        System.out.println(formPassToDBPass(inputPassFormPass("123456"), "f56wet15"));
        System.out.println(inputPassToDBPass("123456", "f56wet15"));
    }
}
