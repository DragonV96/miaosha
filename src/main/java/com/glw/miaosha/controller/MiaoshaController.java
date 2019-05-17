package com.glw.miaosha.controller;

import com.glw.miaosha.doman.MsOrder;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.doman.OrderInfo;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.result.CodeMsg;
import com.glw.miaosha.service.GoodsService;
import com.glw.miaosha.service.MiaoshaService;
import com.glw.miaosha.service.MsUserService;
import com.glw.miaosha.service.OrderService;
import com.glw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Create by glw
 * 2019/5/16 23:06
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    public MsUserService msUserService;

    @Autowired
    public RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String list(Model model, MsUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return "login";
        }
        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_failed";
        }
        // 判断是否已经秒杀到
        MsOrder msOrder = orderService.getMsOrderByUserIdGoodsId(user.getId(), goodsId);
        if (msOrder != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAO_SHA.getMsg());
            return "miaosha_failed";
        }
        // 减库存->下订单->写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);

        return "order_detail";
    }

}
