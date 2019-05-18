package com.glw.miaosha.controller;

import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.result.Result;
import com.glw.miaosha.service.GoodsService;
import com.glw.miaosha.service.MsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Create by glw
 * 2019/5/17 23:59
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public MsUserService msUserService;

    @Autowired
    public RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MsUser> info(Model model, MsUser user) {
        return Result.success(user);
    }
}
