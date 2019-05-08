package com.glw.miaosha.vo;

/**
 * @author glw
 * @date 2019/5/8 10:01
 */
public class LoginVo {
    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "[mobile]:" + this.mobile + " [password]:" + this.password;

    }
}
