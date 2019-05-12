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

import javax.servlet.http.HttpServletResponse;

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
    public String list(Model model, MsUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }

}
