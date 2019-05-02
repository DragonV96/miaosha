package com.glw.miaosha.controller;

import com.glw.miaosha.doman.User;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.redis.UserKey;
import com.glw.miaosha.result.CodeMsg;
import com.glw.miaosha.result.Result;
import com.glw.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author glw
 * @date 2019/4/30 9:55
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    public UserService userService;

    @Autowired
    public RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "glw");
        return "hello";
    }

    @RequestMapping("/hello")
    public Result<String> hello() {
        return Result.success("Hello World++++");
    }

    @RequestMapping("/helloError")
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(2);
        return Result.success(user);
    }

    @RequestMapping("/db/ts")
    @ResponseBody
    public Result<Boolean> dbTs() {
        userService.ts();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("glw");
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }
}
