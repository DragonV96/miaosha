package com.glw.miaosha.vo;

import com.glw.miaosha.dao.doman.OrderInfo;
import lombok.Data;

/**
 * @author glw
 * @date 2019/5/30 15:34
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;
}
