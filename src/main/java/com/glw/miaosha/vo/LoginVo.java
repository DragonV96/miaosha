package com.glw.miaosha.vo;

import com.glw.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author glw
 * @date 2019/5/8 10:01
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    @Override
    public String toString() {
        return "[mobile]:" + this.mobile + " [password]:" + this.password;

    }
}
