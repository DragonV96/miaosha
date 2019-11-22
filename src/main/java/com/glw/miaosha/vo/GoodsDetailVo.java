package com.glw.miaosha.vo;

import com.glw.miaosha.dao.doman.MsUser;
import lombok.Data;

/**
 * Create by glw
 * 2019/5/27 21:15
 */
@Data
public class GoodsDetailVo {
    private int msStatus;
    private int remainSeconds;
    private GoodsVo goods;
    private MsUser user;
}
