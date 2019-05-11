package com.glw.miaosha.controller;

import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.service.MsUserService;
import com.glw.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

/**
 * Create by glw
 * 2019/5/11 1:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    public MsUserService msUserService;

    @Autowired
    public RedisService redisService;

    @RequestMapping("/to_list")
    public String toLogin(Model model, @CookieValue(value = MsUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                          @RequestParam(value = MsUserService.COOKIE_NAME_TOKEN, required = false) String paramToken) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        // 优先取cookie里面的值，没有则取request的值
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MsUser user = msUserService.getByToken(token);
        model.addAttribute("user", user);
        return "goods_list";
    }
}
