package com.glw.miaosha.service;

import com.glw.miaosha.dao.MsUserDao;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.result.CodeMsg;
import com.glw.miaosha.util.MD5Util;
import com.glw.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author glw
 * @date 2019/5/8 11:03
 */
@Service
public class MsUserService {

    @Autowired
    public MsUserDao msUserDao;

    public MsUser getById (long id) {
        return msUserDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {
        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是存在
        MsUser msUser = getById(Long.parseLong(mobile));
        if (msUser ==null) {
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        // 验证密码
        String dbPass = msUser.getPassword();       // 数据库的密码
        String saltDB = msUser.getSalt();
        String clacPass = MD5Util.formPassToDBPass(formPass, saltDB);   // 前台第一次md5后的密码再进行一次md5
        if (clacPass.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
