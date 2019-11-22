package com.glw.miaosha.service;

import com.glw.miaosha.dao.doman.MsOrder;
import com.glw.miaosha.dao.doman.MsUser;
import com.glw.miaosha.dao.doman.OrderInfo;
import com.glw.miaosha.redis.MiaoshaKey;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.util.MD5Util;
import com.glw.miaosha.util.UUIDUtil;
import com.glw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Create by glw
 * 2019/5/16 23:18
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MsUser user, GoodsVo goods) {
        // 减库存->下订单->写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            // order_info ms_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MsOrder order = orderService.getMsOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {    // 秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {   // 没抢到
                return -1;
            } else {        // 抢到了
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }


    public boolean checkPath(MsUser user, long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String oldPath = redisService.get(MiaoshaKey.getMiaoshaPath, "" + user.getId() + "_" + goodsId, String.class);
        return path.equals(oldPath);
    }

    public String createMiaoshaPath(MsUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        String uuid = MD5Util.md5(UUIDUtil.uuid() + "miaosha");
        redisService.set(MiaoshaKey.getMiaoshaPath, "" + user.getId() + "_" + goodsId, uuid);
        return uuid;
    }

    public BufferedImage createVerifyCode(MsUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        int width = 80;
        int height = 32;
        // 创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 设置背景颜色
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // 绘制面板
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 创建一个随机数对象
        Random random = new Random();
        // 创建50个随机点
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // 生成一个随机验证码
        String verifyCode = generateVerifyCode(random);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        // 把验证存到redis中
        int ran = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId, ran);
        // 输出图片
        return image;
    }

    public boolean checkVerifyCode(MsUser user, long goodsId, int verifyCode) {
        if (user == null || goodsId <= 0) {
            return false;
        }
        Integer oldCode = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId, Integer.class);
        if (oldCode == null || oldCode - verifyCode != 0) {
            return false;
        }
        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId);
        return true;
    }

    /**
     * 计算验证码结果
     */
    private static int calc(String expression) {
        try{
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(expression);

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[]{'+', '-', '*'};
    /**
     * 只做+ - *（方便计算，除法较复杂先不做）
     * @param random
     * @return
     */
    private String generateVerifyCode(Random random) {
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        char op1 = ops[random.nextInt(3)];
        char op2 = ops[random.nextInt(3)];
        String expression = "" + num1 + op1 + num2 + op2 + num3;
        return expression;
    }

    public static void main(String[] args) {
        System.out.println(calc("1+3-8"));
    }
}
