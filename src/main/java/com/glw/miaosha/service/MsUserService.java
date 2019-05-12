package com.glw.miaosha.service;

import com.glw.miaosha.dao.MsUserDao;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.exception.GlobalException;
import com.glw.miaosha.redis.MsUserKey;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.result.CodeMsg;
import com.glw.miaosha.util.MD5Util;
import com.glw.miaosha.util.UUIDUtil;
import com.glw.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author glw
 * @date 2019/5/8 11:03
 */
@Service
public class MsUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    public MsUserDao msUserDao;

    @Autowired
    public RedisService redisService;

    public MsUser getById (long id) {
        return msUserDao.getById(id);
    }

    public MsUser getByToken (HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MsUser msUser = redisService.get(MsUserKey.token, token, MsUser.class);
        // 延长token有效期
        if (msUser != null) {
            addCookie(response, msUser);
        }
        return msUser;
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是存在
        MsUser msUser = getById(Long.parseLong(mobile));
        if (msUser ==null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPass = msUser.getPassword();       // 数据库的密码
        String saltDB = msUser.getSalt();
        String clacPass = MD5Util.formPassToDBPass(formPass, saltDB);   // 前台第一次md5后的密码再进行一次md5
        if (!clacPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // 生成cookie
        addCookie(response, msUser);
        return true;
    }

    private void addCookie(HttpServletResponse response, MsUser msUser) {
        String token = UUIDUtil.uuid();
        redisService.set(MsUserKey.token, token, msUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MsUserKey.token.expireSeconds());      // 设置cookie有效期
        cookie.setPath("/");      // 设置网站根目录
        response.addCookie(cookie);
    }
}
