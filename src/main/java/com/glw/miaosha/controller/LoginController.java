package com.glw.miaosha.controller;

import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.result.CodeMsg;
import com.glw.miaosha.result.Result;
import com.glw.miaosha.service.MsUserService;
import com.glw.miaosha.service.UserService;
import com.glw.miaosha.util.ValidatorUtil;
import com.glw.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;

/**
 * @author glw
 * @date 2019/5/8 9:15
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public MsUserService msUserService;

    @Autowired
    public RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
        // 登录
        msUserService.login(loginVo);
        return Result.success(true);
    }
}
