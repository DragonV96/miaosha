package com.glw.miaosha.controller;

import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.service.GoodsService;
import com.glw.miaosha.service.MsUserService;
import com.glw.miaosha.service.UserService;
import com.glw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model,MsUser user) {
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,MsUser user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int msStatus = 0;       // 秒杀状态：0-未开始，1-进行中，2-已结束
        int remainSeconds = 0;  // 秒杀倒计时时间（s）

        if (now < startAt) {    // 秒杀还没开始，倒计时
            msStatus = 0;
            remainSeconds = (int)(startAt - now)/1000;
        } else if (now > endAt) {  // 秒杀已经结束
            msStatus = 2;
            remainSeconds = -1;
        } else {    // 秒杀进行中
            msStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("msStatus", msStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
