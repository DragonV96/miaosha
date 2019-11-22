package com.glw.miaosha.dao.doman;

import lombok.Data;

import java.util.Date;

/**
 * @author glw
 * @date 2019/5/8 10:38
 */
@Data
public class MsUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
