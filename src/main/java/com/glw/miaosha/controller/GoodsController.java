package com.glw.miaosha.controller;

import com.glw.miaosha.dao.doman.MsUser;
import com.glw.miaosha.redis.GoodsKey;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.exception.result.Result;
import com.glw.miaosha.service.GoodsService;
import com.glw.miaosha.service.MsUserService;
import com.glw.miaosha.vo.GoodsDetailVo;
import com.glw.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MsUser user) {
        model.addAttribute("user", user);
        // 取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        // 手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        // 手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response,
                         Model model,MsUser user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        // 取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        // 手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

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
//        return "goods_detail";

        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response,
                                        Model model, MsUser user, @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

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
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setMsStatus(msStatus);
        vo.setRemainSeconds(remainSeconds);
        return Result.success(vo);
    }
}
